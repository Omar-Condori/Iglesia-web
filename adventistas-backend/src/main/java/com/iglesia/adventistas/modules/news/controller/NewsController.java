package com.iglesia.adventistas.modules.news.controller;

import com.iglesia.adventistas.modules.news.dto.CreateNewsRequest;
import com.iglesia.adventistas.modules.news.dto.NewsDTO;
import com.iglesia.adventistas.modules.news.service.NewsService;
import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "Gestión de noticias")
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    @PreAuthorize("hasAuthority('news.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear noticia")
    public ResponseEntity<BaseResponse<NewsDTO>> createNews(
            @Valid @RequestBody CreateNewsRequest request,
            Authentication authentication) {

        // El ID del autor se obtiene del usuario autenticado
        Long authorId = 1L; // Aquí deberías obtenerlo del Authentication

        NewsDTO news = newsService.createNews(request, authorId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(news, "Noticia creada exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar noticias publicadas", description = "Endpoint público")
    public ResponseEntity<BaseResponse<Page<NewsDTO>>> getPublishedNews(Pageable pageable) {
        Page<NewsDTO> news = newsService.getPublishedNews(pageable);
        return ResponseEntity.ok(BaseResponse.success(news));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener noticia por ID")
    public ResponseEntity<BaseResponse<NewsDTO>> getNewsById(@PathVariable Long id) {
        NewsDTO news = newsService.getNewsById(id);
        return ResponseEntity.ok(BaseResponse.success(news));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener noticia por slug")
    public ResponseEntity<BaseResponse<NewsDTO>> getNewsBySlug(@PathVariable String slug) {
        NewsDTO news = newsService.getNewsBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(news));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('news.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar noticia")
    public ResponseEntity<BaseResponse<NewsDTO>> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody CreateNewsRequest request) {

        NewsDTO news = newsService.updateNews(id, request);
        return ResponseEntity.ok(BaseResponse.success(news, "Noticia actualizada exitosamente"));
    }

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('news.publish')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Publicar noticia")
    public ResponseEntity<BaseResponse<Void>> publishNews(@PathVariable Long id) {
        newsService.publishNews(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Noticia publicada exitosamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('news.delete')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar noticia")
    public ResponseEntity<BaseResponse<Void>> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Noticia eliminada exitosamente"));
    }
}
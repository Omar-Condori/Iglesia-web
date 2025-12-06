package com.iglesia.adventistas.modules.homevisits.controller;

import com.iglesia.adventistas.modules.homevisits.dto.CreateHomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.dto.HomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.service.HomeVisitRequestService;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.shared.base.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home-visit-requests")
@RequiredArgsConstructor
public class HomeVisitRequestController {

    private final HomeVisitRequestService homeVisitRequestService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<HomeVisitRequestDTO>> createRequest(
            @Valid @RequestBody CreateHomeVisitRequestDTO dto,
            Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        HomeVisitRequestDTO request = homeVisitRequestService.createRequest(dto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(request, "Solicitud de visita creada exitosamente"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('home-visits.view')")
    public ResponseEntity<BaseResponse<Page<HomeVisitRequestDTO>>> getAllRequests(Pageable pageable) {
        Page<HomeVisitRequestDTO> requests = homeVisitRequestService.getAllRequests(pageable);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('home-visits.view')")
    public ResponseEntity<BaseResponse<Page<HomeVisitRequestDTO>>> getRequestsByStatus(
            @PathVariable RequestStatus status,
            Pageable pageable) {
        Page<HomeVisitRequestDTO> requests = homeVisitRequestService.getRequestsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('home-visits.view')")
    public ResponseEntity<BaseResponse<HomeVisitRequestDTO>> getRequestById(@PathVariable Long id) {
        HomeVisitRequestDTO request = homeVisitRequestService.getRequestById(id);
        return ResponseEntity.ok(BaseResponse.success(request));
    }

    @GetMapping("/my-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<HomeVisitRequestDTO>>> getMyRequests(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<HomeVisitRequestDTO> requests = homeVisitRequestService.getMyRequests(userId);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('home-visits.manage')")
    public ResponseEntity<BaseResponse<HomeVisitRequestDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status,
            Authentication authentication) {

        Long attendedBy = Long.parseLong(authentication.getName());
        HomeVisitRequestDTO request = homeVisitRequestService.updateStatus(id, status, attendedBy);

        return ResponseEntity.ok(BaseResponse.success(request, "Estado actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('home-visits.manage')")
    public ResponseEntity<BaseResponse<Void>> deleteRequest(@PathVariable Long id) {
        homeVisitRequestService.deleteRequest(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Solicitud eliminada exitosamente"));
    }

    @GetMapping("/stats/count-by-status")
    @PreAuthorize("hasAuthority('home-visits.view')")
    public ResponseEntity<BaseResponse<Long>> countByStatus(@RequestParam RequestStatus status) {
        long count = homeVisitRequestService.countByStatus(status);
        return ResponseEntity.ok(BaseResponse.success(count));
    }
}

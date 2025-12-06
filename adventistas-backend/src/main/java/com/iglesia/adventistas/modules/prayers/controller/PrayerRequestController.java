package com.iglesia.adventistas.modules.prayers.controller;

import com.iglesia.adventistas.modules.prayers.dto.CreatePrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.dto.PrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.modules.prayers.service.PrayerRequestService;
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
@RequestMapping("/api/prayer-requests")
@RequiredArgsConstructor
public class PrayerRequestController {

    private final PrayerRequestService prayerRequestService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<PrayerRequestDTO>> createRequest(
            @Valid @RequestBody CreatePrayerRequestDTO dto,
            Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        PrayerRequestDTO request = prayerRequestService.createRequest(dto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(request, "Petición de oración creada exitosamente"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('prayer-requests.view')")
    public ResponseEntity<BaseResponse<Page<PrayerRequestDTO>>> getAllRequests(Pageable pageable) {
        Page<PrayerRequestDTO> requests = prayerRequestService.getAllRequests(pageable);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('prayer-requests.view')")
    public ResponseEntity<BaseResponse<Page<PrayerRequestDTO>>> getRequestsByStatus(
            @PathVariable RequestStatus status,
            Pageable pageable) {
        Page<PrayerRequestDTO> requests = prayerRequestService.getRequestsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('prayer-requests.view')")
    public ResponseEntity<BaseResponse<PrayerRequestDTO>> getRequestById(@PathVariable Long id) {
        PrayerRequestDTO request = prayerRequestService.getRequestById(id);
        return ResponseEntity.ok(BaseResponse.success(request));
    }

    @GetMapping("/my-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<PrayerRequestDTO>>> getMyRequests(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<PrayerRequestDTO> requests = prayerRequestService.getMyRequests(userId);
        return ResponseEntity.ok(BaseResponse.success(requests));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('prayer-requests.manage')")
    public ResponseEntity<BaseResponse<PrayerRequestDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status,
            Authentication authentication) {

        Long attendedBy = Long.parseLong(authentication.getName());
        PrayerRequestDTO request = prayerRequestService.updateStatus(id, status, attendedBy);

        return ResponseEntity.ok(BaseResponse.success(request, "Estado actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('prayer-requests.manage')")
    public ResponseEntity<BaseResponse<Void>> deleteRequest(@PathVariable Long id) {
        prayerRequestService.deleteRequest(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Petición eliminada exitosamente"));
    }

    @GetMapping("/stats/count-by-status")
    @PreAuthorize("hasAuthority('prayer-requests.view')")
    public ResponseEntity<BaseResponse<Long>> countByStatus(@RequestParam RequestStatus status) {
        long count = prayerRequestService.countByStatus(status);
        return ResponseEntity.ok(BaseResponse.success(count));
    }
}

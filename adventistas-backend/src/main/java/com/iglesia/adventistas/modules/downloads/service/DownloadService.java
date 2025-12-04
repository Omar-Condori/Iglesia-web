package com.iglesia.adventistas.modules.downloads.service;

import com.iglesia.adventistas.modules.downloads.dto.CreateDownloadRequest;
import com.iglesia.adventistas.modules.downloads.dto.DownloadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DownloadService {

    DownloadDTO createDownload(CreateDownloadRequest request, Long uploaderId);

    DownloadDTO updateDownload(Long id, CreateDownloadRequest request);

    DownloadDTO getDownloadById(Long id);

    DownloadDTO getDownloadBySlug(String slug);

    Page<DownloadDTO> getAllDownloads(Pageable pageable);

    Page<DownloadDTO> getActiveDownloads(Pageable pageable);

    Page<DownloadDTO> getDownloadsByCategory(Long categoryId, Pageable pageable);

    void deleteDownload(Long id);

    void incrementDownloadCount(Long id);
}
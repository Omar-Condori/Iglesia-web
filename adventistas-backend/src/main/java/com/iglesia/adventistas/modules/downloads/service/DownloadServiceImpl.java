package com.iglesia.adventistas.modules.downloads.service;

import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.departments.repository.DepartmentRepository;
import com.iglesia.adventistas.modules.downloads.dto.CreateDownloadRequest;
import com.iglesia.adventistas.modules.downloads.dto.DownloadDTO;
import com.iglesia.adventistas.modules.downloads.entity.Download;
import com.iglesia.adventistas.modules.downloads.entity.DownloadCategory;
import com.iglesia.adventistas.modules.downloads.mapper.DownloadMapper;
import com.iglesia.adventistas.modules.downloads.repository.DownloadCategoryRepository;
import com.iglesia.adventistas.modules.downloads.repository.DownloadRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DownloadServiceImpl implements DownloadService {

    private final DownloadRepository downloadRepository;
    private final DownloadCategoryRepository downloadCategoryRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final DownloadMapper downloadMapper;

    @Override
    public DownloadDTO createDownload(CreateDownloadRequest request, Long uploaderId) {
        log.info("Creating download: {}", request.getTitle());

        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        DownloadCategory category = downloadCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Download download = downloadMapper.toEntity(request);
        download.setCategory(category);
        download.setUploadedBy(uploader);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
            download.setDepartment(department);
        }

        Download saved = downloadRepository.save(download);
        return downloadMapper.toDTO(saved);
    }

    @Override
    public DownloadDTO updateDownload(Long id, CreateDownloadRequest request) {
        Download download = downloadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Descarga no encontrada"));

        downloadMapper.updateEntity(request, download);

        if (!download.getCategory().getId().equals(request.getCategoryId())) {
            DownloadCategory category = downloadCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            download.setCategory(category);
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
            download.setDepartment(department);
        }

        Download updated = downloadRepository.save(download);
        return downloadMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DownloadDTO getDownloadById(Long id) {
        Download download = downloadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Descarga no encontrada"));

        return downloadMapper.toDTO(download);
    }

    @Override
    @Transactional(readOnly = true)
    public DownloadDTO getDownloadBySlug(String slug) {
        Download download = downloadRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Descarga no encontrada"));

        return downloadMapper.toDTO(download);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DownloadDTO> getAllDownloads(Pageable pageable) {
        return downloadRepository.findAll(pageable)
                .map(downloadMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DownloadDTO> getActiveDownloads(Pageable pageable) {
        return downloadRepository.findByIsActiveTrue(pageable)
                .map(downloadMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DownloadDTO> getDownloadsByCategory(Long categoryId, Pageable pageable) {
        return downloadRepository.findByCategoryId(categoryId, pageable)
                .map(downloadMapper::toDTO);
    }

    @Override
    public void deleteDownload(Long id) {
        Download download = downloadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Descarga no encontrada"));

        downloadRepository.delete(download);
    }

    @Override
    public void incrementDownloadCount(Long id) {
        Download download = downloadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Descarga no encontrada"));

        download.setDownloadsCount(download.getDownloadsCount() + 1);
        downloadRepository.save(download);
    }
}
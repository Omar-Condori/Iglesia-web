package com.iglesia.adventistas.modules.churches.service;

import com.iglesia.adventistas.modules.churches.dto.ChurchDTO;
import com.iglesia.adventistas.modules.churches.dto.CreateChurchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChurchService {

    ChurchDTO createChurch(CreateChurchRequest request);

    ChurchDTO updateChurch(Long id, CreateChurchRequest request);

    ChurchDTO getChurchById(Long id);

    ChurchDTO getChurchBySlug(String slug);

    Page<ChurchDTO> getAllChurches(Pageable pageable);

    Page<ChurchDTO> getActiveChurches(Pageable pageable);

    List<ChurchDTO> getChurchesByUnion(Long unionId);

    List<ChurchDTO> getChurchesByCity(String city);

    List<ChurchDTO> getChurchesByState(String state);

    void deleteChurch(Long id);

    void toggleActive(Long id);
}
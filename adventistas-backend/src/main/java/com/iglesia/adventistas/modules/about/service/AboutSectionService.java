package com.iglesia.adventistas.modules.about.service;

import com.iglesia.adventistas.modules.about.dto.AboutSectionDTO;
import com.iglesia.adventistas.modules.about.dto.UpdateAboutSectionRequest;

import java.util.List;

public interface AboutSectionService {
    List<AboutSectionDTO> getAllActiveSections();

    AboutSectionDTO getSectionBySlug(String slug);

    AboutSectionDTO updateSection(Long id, UpdateAboutSectionRequest request);
}

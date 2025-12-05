package com.iglesia.adventistas.modules.departments.service;

import com.iglesia.adventistas.modules.departments.dto.CreateContentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentContentDTO;

import java.util.List;

public interface DepartmentContentService {

    DepartmentContentDTO createContent(Long sectionId, CreateContentRequest request, Long authorId);

    DepartmentContentDTO updateContent(Long id, CreateContentRequest request);

    void deleteContent(Long id);

    DepartmentContentDTO getContentById(Long id);

    List<DepartmentContentDTO> getContentBySection(Long sectionId, boolean publishedOnly);

    void togglePublish(Long id);
}

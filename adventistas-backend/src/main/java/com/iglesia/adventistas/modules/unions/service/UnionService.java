package com.iglesia.adventistas.modules.unions.service;

import com.iglesia.adventistas.modules.unions.dto.CreateUnionRequest;
import com.iglesia.adventistas.modules.unions.dto.UnionDTO;

import java.util.List;

public interface UnionService {

    UnionDTO createUnion(CreateUnionRequest request);

    UnionDTO updateUnion(Long id, CreateUnionRequest request);

    UnionDTO getUnionById(Long id);

    UnionDTO getUnionBySlug(String slug);

    List<UnionDTO> getAllUnions();

    List<UnionDTO> getActiveUnions();

    List<UnionDTO> getUnionsByCountry(String country);

    void deleteUnion(Long id);

    void toggleActive(Long id);
}
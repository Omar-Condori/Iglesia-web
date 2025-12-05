package com.iglesia.adventistas.modules.departments.service;

import com.iglesia.adventistas.modules.departments.dto.CreateDepartmentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(CreateDepartmentRequest request);

    DepartmentDTO getDepartmentById(Long id);

    DepartmentDTO getBySlug(String slug);

    List<DepartmentDTO> getAllDepartments();

    List<DepartmentDTO> getActiveDepartments();

    DepartmentDTO updateDepartment(Long id, CreateDepartmentRequest request);

    void deleteDepartment(Long id);

    void toggleActive(Long id);
}
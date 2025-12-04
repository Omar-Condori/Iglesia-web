package com.iglesia.adventistas.modules.departments.service;

import com.iglesia.adventistas.modules.departments.dto.CreateDepartmentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(CreateDepartmentRequest request);

    DepartmentDTO updateDepartment(Long id, CreateDepartmentRequest request);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    List<DepartmentDTO> getActiveDepartments();

    void deleteDepartment(Long id);

    void toggleActive(Long id);
}
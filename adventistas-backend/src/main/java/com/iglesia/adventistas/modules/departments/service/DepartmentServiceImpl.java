package com.iglesia.adventistas.modules.departments.service;

import com.iglesia.adventistas.modules.departments.dto.CreateDepartmentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentDTO;
import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.departments.mapper.DepartmentMapper;
import com.iglesia.adventistas.modules.departments.repository.DepartmentRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDTO createDepartment(CreateDepartmentRequest request) {
        log.info("Creating department: {}", request.getName());

        Department department = departmentMapper.toEntity(request);

        // Generar slug único
        String baseSlug = com.iglesia.adventistas.shared.util.SlugUtils.generateSlug(request.getName());
        String finalSlug = baseSlug;
        int counter = 1;

        while (departmentRepository.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + "-" + counter;
            counter++;
        }
        department.setSlug(finalSlug);

        Department saved = departmentRepository.save(department);

        log.info("Department created with ID: {}", saved.getId());
        return departmentMapper.toDTO(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, CreateDepartmentRequest request) {
        log.info("Updating department ID: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));

        departmentMapper.updateEntity(request, department);
        Department updated = departmentRepository.save(department);

        return departmentMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));

        return departmentMapper.toDTO(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getActiveDepartments() {
        return departmentRepository.findByIsActiveTrueOrderBySortOrder().stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));

        departmentRepository.delete(department);
        log.info("Department deleted: {}", id);
    }

    @Override
    public void toggleActive(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));

        department.setIsActive(!department.getIsActive());
        departmentRepository.save(department);

        log.info("Department {} active status: {}", id, department.getIsActive());
    }
}
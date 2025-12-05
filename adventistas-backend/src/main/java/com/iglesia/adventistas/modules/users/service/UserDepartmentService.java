package com.iglesia.adventistas.modules.users.service;

import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.departments.repository.DepartmentRepository;
import com.iglesia.adventistas.modules.users.dto.AssignDepartmentRequest;
import com.iglesia.adventistas.modules.users.dto.UserDepartmentDTO;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.entity.UserDepartment;
import com.iglesia.adventistas.modules.users.repository.UserDepartmentRepository;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

    private final UserDepartmentRepository userDepartmentRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public List<UserDepartmentDTO> getUserDepartments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return userDepartmentRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDepartmentDTO assignDepartment(Long userId, AssignDepartmentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));

        UserDepartment userDept = userDepartmentRepository
                .findByUserIdAndDepartmentId(userId, request.getDepartmentId())
                .orElse(new UserDepartment());

        userDept.setUser(user);
        userDept.setDepartment(department);
        userDept.setCanView(request.getCanView() != null ? request.getCanView() : true);
        userDept.setCanEdit(request.getCanEdit() != null ? request.getCanEdit() : false);
        userDept.setCanPublish(request.getCanPublish() != null ? request.getCanPublish() : false);

        userDept = userDepartmentRepository.save(userDept);
        return toDTO(userDept);
    }

    @Transactional
    public void removeDepartment(Long userId, Long departmentId) {
        userDepartmentRepository.deleteByUserIdAndDepartmentId(userId, departmentId);
    }

    private UserDepartmentDTO toDTO(UserDepartment entity) {
        return UserDepartmentDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .departmentId(entity.getDepartment().getId())
                .departmentName(entity.getDepartment().getName())
                .departmentSlug(entity.getDepartment().getSlug())
                .canView(entity.getCanView())
                .canEdit(entity.getCanEdit())
                .canPublish(entity.getCanPublish())
                .build();
    }
}

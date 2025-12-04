package com.iglesia.adventistas.modules.users.service;

import com.iglesia.adventistas.modules.roles.entity.Role;
import com.iglesia.adventistas.modules.roles.repository.RoleRepository;
import com.iglesia.adventistas.modules.users.dto.CreateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UpdateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UserResponse;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.exception.UserNotFoundException;
import com.iglesia.adventistas.modules.users.mapper.UserMapper;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());

        // Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        // Crear usuario
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Asignar roles
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Validar email único si cambió
        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está en uso");
        }

        userMapper.updateEntityFromDTO(request, user);

        // Actualizar roles si se enviaron
        if (request.getRoleIds() != null) {
            Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());
            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Getting user with ID: {}", id);

        User user = userRepository.findActiveById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Getting all users with pagination");

        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Soft deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setDeletedAt(LocalDateTime.now());
        user.setIsActive(false);
        userRepository.save(user);

        log.info("User soft deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.info("Getting user by email: {}", email);

        User user = userRepository.findActiveByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        return userMapper.toResponse(user);
    }
}
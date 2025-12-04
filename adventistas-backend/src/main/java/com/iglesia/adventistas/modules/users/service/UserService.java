package com.iglesia.adventistas.modules.users.service;

import com.iglesia.adventistas.modules.users.dto.CreateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UpdateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    void deleteUser(Long id);

    UserResponse getUserByEmail(String email);
}
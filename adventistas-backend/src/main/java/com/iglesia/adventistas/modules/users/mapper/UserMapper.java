package com.iglesia.adventistas.modules.users.mapper;

import com.iglesia.adventistas.modules.users.dto.CreateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UpdateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UserDTO;
import com.iglesia.adventistas.modules.users.dto.UserResponse;
import com.iglesia.adventistas.modules.users.entity.User;

import java.util.Set;

public interface UserMapper {
    UserDTO toDTO(User user);

    UserResponse toResponse(User user);

    User toEntity(CreateUserRequest request);

    void updateEntityFromDTO(UpdateUserRequest dto, User entity);

    Set<String> mapRoles(User user);
}
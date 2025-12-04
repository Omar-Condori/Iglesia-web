package com.iglesia.adventistas.modules.users.exception;

import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super("Usuario no encontrado con ID: " + id);
    }
}
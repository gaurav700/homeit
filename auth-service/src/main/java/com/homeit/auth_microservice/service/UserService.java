package com.homeit.auth_microservice.service;

import com.homeit.auth_microservice.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> createUser(String email, String password, String userType);

    Optional<UserDTO> findByEmail(String email);

    boolean validateUser(String email, String password);
}

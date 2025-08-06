package com.homeit.auth_microservice.service.impl;

import com.homeit.auth_microservice.dto.UserConverter;
import com.homeit.auth_microservice.dto.UserDTO;
import com.homeit.auth_microservice.entities.UserEntity;
import com.homeit.auth_microservice.repository.UserRepository;
import com.homeit.auth_microservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDTO> createUser(String email, String password, String userType) {

        if(userRepository.findByEmail(email).isPresent()){
            return Optional.empty();
        }
        return Optional.of(
                userRepository
                        .save(new UserEntity(
                            UUID.randomUUID(),
                            email,
                            passwordEncoder.encode(password),
                            userType)))
                        .map(UserConverter::fromUserEntity);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserConverter::fromUserEntity);
    }

    @Override
    public boolean validateUser(String email, String password) {
        return userRepository
                .findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}

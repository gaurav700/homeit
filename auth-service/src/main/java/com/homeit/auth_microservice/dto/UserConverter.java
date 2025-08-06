package com.homeit.auth_microservice.dto;

import com.homeit.auth_microservice.entities.UserEntity;

public class UserConverter {
    public static UserDTO fromUserEntity(UserEntity user) {
        return new UserDTO(user.getId().toString(), user.getEmail(), null, user.getUserType());
    }
}
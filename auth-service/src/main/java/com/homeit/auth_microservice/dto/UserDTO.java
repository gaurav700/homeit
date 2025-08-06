package com.homeit.auth_microservice.dto;

public record UserDTO(String id, String email, String password, String user_type) {
}
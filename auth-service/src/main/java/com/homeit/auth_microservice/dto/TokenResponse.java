package com.homeit.auth_microservice.dto;

public record TokenResponse(
        String access_token,
        String token_type,
        String expires_in,
        String scope
)
{}
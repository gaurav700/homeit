package com.homeit.auth_microservice.service;

import com.homeit.auth_microservice.dto.TokenRequest;
import com.homeit.auth_microservice.dto.TokenResponse;

public interface JwtService {
    TokenResponse getJWTToken(TokenRequest tokenRequest, String scope, String userId);
}

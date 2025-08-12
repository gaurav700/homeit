package com.homeit.revoke_token_service.service;

import com.homeit.revoke_token_service.entities.RevokedToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface RevokeTokenService {
    Mono<RevokedToken> revokeToken(String token);

    Mono<RevokedToken> getRevokedToken(String token);
}

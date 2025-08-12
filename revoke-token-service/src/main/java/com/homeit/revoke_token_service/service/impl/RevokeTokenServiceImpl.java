package com.homeit.revoke_token_service.service.impl;

import com.homeit.revoke_token_service.entities.RevokedToken;
import com.homeit.revoke_token_service.repository.RevokeTokenRepository;
import com.homeit.revoke_token_service.service.RevokeTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RevokeTokenServiceImpl implements RevokeTokenService {

    private final RevokeTokenRepository repository;

    public RevokeTokenServiceImpl(RevokeTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<RevokedToken> revokeToken(String token) {
        return repository.save(new RevokedToken(token));
    }

    @Override
    public Mono<RevokedToken> getRevokedToken(String token) {
        return repository.getByRevoked(token);
    }
}

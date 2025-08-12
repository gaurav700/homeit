package com.homeit.rental_property_microservices.configuration;

import com.homeit.rental_property_microservices.tokenclients.RestTemplateRevokedTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenRevocationFilter extends OncePerRequestFilter {

    private final RestTemplateRevokedTokenService revokeTokenService;

    public TokenRevocationFilter(RestTemplateRevokedTokenService revokeTokenService) {
        this.revokeTokenService = revokeTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("TokenRevocationFilter is called");

        // Extract token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Validate the token against the revocation service
            if (revokeTokenService.isTokenRevoked(token)) {
                response.setHeader(
                        "WWW-Authenticate",
                        "Bearer error=\"invalid_token\", error_description=\"Token is revoked\""
                );
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is revoked");
                return;
            }

        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
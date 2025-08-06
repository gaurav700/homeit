package com.homeit.auth_microservice.dto;

public record TokenRequest (String grant_type,
                            String username,
                            String password,
                            String client_id,
                            String client_secret)
{}
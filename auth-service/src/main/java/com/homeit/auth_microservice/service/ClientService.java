package com.homeit.auth_microservice.service;

public interface ClientService {
    boolean validateClient(String clientId, String clientSecret);
}

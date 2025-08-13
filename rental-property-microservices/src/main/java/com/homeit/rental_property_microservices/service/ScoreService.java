package com.homeit.rental_property_microservices.service;

import java.util.UUID;

public interface ScoreService {
    void addScore(UUID propertyId);
}
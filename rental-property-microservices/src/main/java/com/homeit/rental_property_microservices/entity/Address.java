package com.homeit.rental_property_microservices.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
public record Address (
    String streetAddress,
    String city,
    String zip,
    String country
){}
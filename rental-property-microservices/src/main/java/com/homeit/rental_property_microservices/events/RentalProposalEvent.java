package com.homeit.rental_property_microservices.events;

public record RentalProposalEvent(
        String proposalId,
        String roundId,
        String propertyId,
        String roundType) {
}
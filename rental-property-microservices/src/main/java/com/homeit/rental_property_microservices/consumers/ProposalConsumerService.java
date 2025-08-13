package com.homeit.rental_property_microservices.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeit.rental_property_microservices.events.RentalProposalEvent;
import com.homeit.rental_property_microservices.service.ScoreService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProposalConsumerService {
    private final ScoreService scoreService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProposalConsumerService(
            @Qualifier("jdbcRentalPropertyService") ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @KafkaListener(topics = "proposal-topic", groupId = "proposal-group")
    public void consume(String message) throws JsonProcessingException {
        RentalProposalEvent event = objectMapper.readValue(message, RentalProposalEvent.class);
        scoreService.addScore(UUID.fromString(event.propertyId()));
        System.out.println("Consumed message: " + event);
    }
}
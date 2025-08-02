package com.homeit.rental_property_microservices.dto.utils;

import com.homeit.rental_property_microservices.dto.RentalPropertyDTO;
import com.homeit.rental_property_microservices.entity.Address;
import com.homeit.rental_property_microservices.entity.RentalProperty;

public class RentalPropertyConverter {
    public static RentalProperty toEntity(RentalPropertyDTO dto){
        return RentalProperty.builder()
                .rent(dto.rent())
                .id(dto.id())
                .name(dto.name())
                .landlordID(dto.landlordID())
                .address(Address.builder()
                        .zip(dto.zipCode())
                        .city(dto.city())
                        .streetAddress(dto.address())
                        .country(dto.country())
                        .build())
                .build();
    }

    public static RentalPropertyDTO toDTO(RentalProperty entity) {
        return RentalPropertyDTO.builder()
                .address(entity.getAddress().streetAddress())
                .name(entity.getName())
                .rent(entity.getRent())
                .city(entity.getAddress().city())
                .landlordID(entity.getLandlordID())
                .id(entity.getId())
                .country(entity.getAddress().country())
                .zipCode(entity.getAddress().zip())
                .build();
    }




}

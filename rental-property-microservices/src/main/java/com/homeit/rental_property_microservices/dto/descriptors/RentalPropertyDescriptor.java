package com.homeit.rental_property_microservices.dto.descriptors;


import com.homeit.rental_property_microservices.dto.RentalPropertyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class RentalPropertyDescriptor extends RepresentationModel<RentalPropertyDescriptor> {
    private RentalPropertyDTO rentalProperty;
}


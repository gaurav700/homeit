package com.homeit.rental_property_microservices.service.impl;

import com.homeit.rental_property_microservices.dto.RentalPropertyDTO;
import com.homeit.rental_property_microservices.dto.utils.RentalPropertyConverter;
import com.homeit.rental_property_microservices.entity.RentalProperty;
import com.homeit.rental_property_microservices.service.RentalPropertyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("entityManagerRentalPropertyService")
public class RentalPropertyServiceEntityManagerImpl implements RentalPropertyService {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<RentalPropertyDTO> getAllProperties() {
        return List.of();
    }

    @Override
    public Optional<RentalPropertyDTO> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public Page<RentalPropertyDTO> getPagedProperties(int page, int size) {
        return null;
    }

    @Override
    public RentalPropertyDTO create(RentalPropertyDTO property) {
        return null;
    }

    @Override
    public Optional<RentalPropertyDTO> update(UUID id, RentalPropertyDTO updatedProperty) {
        return Optional.empty();
    }

    @Override
    public Optional<RentalPropertyDTO> updateSomeFields(UUID id, RentalPropertyDTO partialUpdate) {
        return Optional.empty();
    }

    @Override
    public Optional<RentalPropertyDTO> delete(UUID id) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        RentalPropertyDTO dto;
        try{
            entityTransaction.begin();

            RentalProperty property = entityManager.find(RentalProperty.class, id);
            dto = RentalPropertyConverter.toDTO(property);
            entityManager.remove(property);

            entityTransaction.commit();
        }
        catch (Exception e){
            if(entityTransaction.isActive()){
                entityTransaction.rollback();
            }
            throw e;
        }
        finally {
            entityManager.close();
        }
        return Optional.ofNullable(dto);
    }

    @Override
    public List<RentalPropertyDTO> search(String name, String address, String city, String country, String zipCode) {
        return List.of();
    }
}

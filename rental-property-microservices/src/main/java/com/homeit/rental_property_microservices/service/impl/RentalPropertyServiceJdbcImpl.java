package com.homeit.rental_property_microservices.service.impl;

import com.homeit.rental_property_microservices.dto.RentalPropertyDTO;
import com.homeit.rental_property_microservices.service.RentalPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("jdbcRentalPropertyService")
public class RentalPropertyServiceJdbcImpl implements RentalPropertyService {

    @Autowired
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private final RentalPropertyRowMapper rentalPropertyRowMapper;

    public RentalPropertyServiceJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, RentalPropertyRowMapper rentalPropertyRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rentalPropertyRowMapper = rentalPropertyRowMapper;
    }


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
    public Optional<RentalPropertyDTO> delete(UUID id, String userId) {
        return Optional.empty();
    }

    @Override
    public List<RentalPropertyDTO> search(String name, String address, String city, String country, String zipCode) {

        StringBuilder sql = new StringBuilder("SELECT * FROM rental_properties WHERE 1=1");
        MapSqlParameterSource params =  new MapSqlParameterSource();

        if (StringUtils.hasText(name)) {
            sql.append(" AND name LIKE :name");
            params.addValue("name", "%" + name + "%");
        }
        if (StringUtils.hasText(address)) {
            sql.append(" AND address LIKE :address");
            params.addValue("address", "%" + address + "%");
        }
        if (StringUtils.hasText(city)) {
            sql.append(" AND address LIKE :city");
            params.addValue("city", "%" + city + "%");
        }
        if (StringUtils.hasText(country)) {
            sql.append(" AND address LIKE :country");
            params.addValue("country", "%" + country + "%");
        }

        if (StringUtils.hasText(zipCode)) {
            sql.append(" AND address LIKE :zipCode");
            params.addValue("zipCode", "%" + zipCode + "%");
        }
        return jdbcTemplate.query(
                sql.toString(), params, rentalPropertyRowMapper);
    }
}

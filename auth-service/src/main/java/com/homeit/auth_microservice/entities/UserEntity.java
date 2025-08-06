package com.homeit.auth_microservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String userType;
}

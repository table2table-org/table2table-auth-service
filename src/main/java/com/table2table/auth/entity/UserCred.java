package com.table2table.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_cred")
public class UserCred {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credId;

    private String name;
    private String email;
    private String password;
    private String role;  // COOK, CUSTOMER, ADMIN

    private LocalDateTime createdOn;
    private boolean isLocked;
}


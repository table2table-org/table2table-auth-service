package com.table2table.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String flatNumber;
    private String floor;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long credId;
    private Long communityId;
}
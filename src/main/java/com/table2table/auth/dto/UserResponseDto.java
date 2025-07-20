package com.table2table.auth.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String flatNumber;
    private String floor;
    private String role;
}
package com.table2table.auth.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String flatNumber;
    private String floor;
    private String role;
}

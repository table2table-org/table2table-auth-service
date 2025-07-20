package com.table2table.auth.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Long communityId;
    private Long credId;
    private String flatNumber;
    private String floor;
    private String role;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
}

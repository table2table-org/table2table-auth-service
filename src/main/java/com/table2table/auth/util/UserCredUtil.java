package com.table2table.auth.util;



import com.table2table.auth.dto.UserResponseDto;
import com.table2table.auth.entity.UserCred;

import java.util.List;
import java.util.stream.Collectors;

public class UserCredUtil {

    public static UserResponseDto convertToDto(UserCred userCred) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userCred.getCredId());
        dto.setName(userCred.getName());
        dto.setEmail(userCred.getEmail());;
        dto.setRole(userCred.getRole());
        return dto;
    }

    public static List<UserResponseDto> convertToDtoList(List<UserCred> userCreds) {
        return userCreds.stream()
                .map(UserCredUtil::convertToDto)
                .collect(Collectors.toList());
    }


}

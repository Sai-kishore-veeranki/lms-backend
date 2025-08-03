package com.vsk.lms.user_service.dto;

import com.vsk.lms.user_service.model.enums.Role;
import lombok.*;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}


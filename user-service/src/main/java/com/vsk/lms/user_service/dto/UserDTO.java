package com.vsk.lms.user_service.dto;

import com.vsk.lms.user_service.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
}

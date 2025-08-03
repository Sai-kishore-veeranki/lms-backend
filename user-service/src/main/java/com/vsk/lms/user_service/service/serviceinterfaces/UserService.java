package com.vsk.lms.user_service.service.serviceinterfaces;


import com.vsk.lms.user_service.dto.UserDto;
import org.springframework.context.annotation.Configuration;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto dto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
}



package com.vsk.lms.user_service.service.serviceinterfaces;

import com.vsk.lms.user_service.dto.CreateUserRequest;
import com.vsk.lms.user_service.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(CreateUserRequest request);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
}

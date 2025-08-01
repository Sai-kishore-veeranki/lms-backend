package com.vsk.lms.user_service.service.serviceimpl;

import com.vsk.lms.user_service.dto.CreateUserRequest;
import com.vsk.lms.user_service.dto.UserDTO;
import com.vsk.lms.user_service.exception.UserAlreadyExistsException;
import com.vsk.lms.user_service.model.User;
import com.vsk.lms.user_service.repository.UserRepository;
import com.vsk.lms.user_service.service.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword()) // Encrypt in real scenario
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}


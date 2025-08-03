package com.vsk.lms.user_service.service.serviceimpl;

import com.vsk.lms.user_service.dto.UserDto;
import com.vsk.lms.user_service.mapper.UserMapper;
import com.vsk.lms.user_service.model.UserProfile;
import com.vsk.lms.user_service.repository.UserRepository;
import com.vsk.lms.user_service.service.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) {
        UserProfile profile = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(profile);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto createUser(UserDto dto) {
        UserProfile profile = userMapper.toEntity(dto);
        profile = userRepository.save(profile);
        return userMapper.toDto(profile);
    }
}

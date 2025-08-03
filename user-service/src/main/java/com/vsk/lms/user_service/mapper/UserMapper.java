package com.vsk.lms.user_service.mapper;

import com.vsk.lms.user_service.dto.UserDto;
import com.vsk.lms.user_service.model.UserProfile;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserProfile profile);
    UserProfile toEntity(UserDto dto);
}


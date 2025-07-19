package com.library.mapper;

import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());

        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setId(user.getId());

        return dto;
    }

    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

package com.library.service;

import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.entity.User;
import com.library.mapper.UserMapper;
import com.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserMapper mapper;

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    User userEntity;
    UserRequestDto userRequestDto;
    UserResponseDto userResponseDto;
    UserResponseDto userResponseDtoUpdated;
    List<User> userListEntity = new ArrayList<>();
    List<UserResponseDto>userResponseDtoList = new ArrayList<>();
    Long userEntityId = 1L;


    @BeforeEach
    void setUp() {
        userEntity = new User();
        userEntity.setId(userEntityId);
        userEntity.setName("Test user");
        userEntity.setEmail("Test Email");

        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        userEntity.setRegistrationDate(fixedDate);

        userListEntity.add(userEntity);

        userResponseDto = new UserResponseDto();
        userResponseDto.setName("Test user");
        userResponseDto.setEmail("Test Email");
        userResponseDto.setRegistrationDate(fixedDate);

        userResponseDtoList.add(userResponseDto);

        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("New test email");
        userRequestDto.setName("New test name");

        userResponseDtoUpdated = new UserResponseDto();
        userResponseDtoUpdated.setName("New test name");
        userResponseDtoUpdated.setEmail("New test email");
        userResponseDtoUpdated.setRegistrationDate(userEntity.getRegistrationDate());
    }

    @Test
    void shouldSaveUser() {
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toEntity(userRequestDto)).thenReturn(userEntity);

        User savedUser = service.save(userRequestDto);

        verify(repository, times(1)).save(userEntity);
        verify(mapper, times(1)).toEntity(userRequestDto);

        assertEquals(savedUser, userEntity);
    }

    @Test
    void shouldFindUserById() {
        when(repository.findById(userEntityId)).thenReturn(Optional.ofNullable(userEntity));
        when(mapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

        UserResponseDto user = service.findById(userEntityId);

        verify(repository, times(1)).findById(userEntityId);
        verify(mapper, times(1)).toResponseDto(userEntity);

        assertEquals(user.getName(), userResponseDto.getName());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
    }

    @Test
    void shouldFindAllUsers() {
        when(repository.findAll()).thenReturn(userListEntity);
        when(mapper.toResponseDtoList(userListEntity)).thenReturn(userResponseDtoList);

        List<UserResponseDto> listedUsers = service.findAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toResponseDtoList(userListEntity);

        assertEquals(listedUsers.getFirst(), userResponseDtoList.getFirst());
    }

    @Test
    void shouldFindUserByName() {
        when(repository.findByName(userEntity.getName())).thenReturn(userEntity);
        when(mapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

        UserResponseDto userFound = service.findByName(userEntity.getName());

        verify(repository,times(1)).findByName(userEntity.getName());
        verify(mapper, times(1)).toResponseDto(userEntity);

        assertEquals(userFound.getName(), userEntity.getName());
    }

    @Test
    void shouldUpdateUser() {
        when(repository.findById(userEntityId)).thenReturn(Optional.of(userEntity));
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toResponseDto(userEntity)).thenReturn(userResponseDtoUpdated);

        UserResponseDto updatedUser = service.update(userEntityId, userRequestDto);

        verify(repository, times(1)).findById(userEntityId);
        verify(repository, times(1)).save(userEntity);
        verify(mapper, times(1)).toResponseDto(userEntity);

        assertEquals(userRequestDto.getName(), updatedUser.getName());
        assertEquals(userRequestDto.getEmail(), updatedUser.getEmail());
    }

    @Test
    void shouldDeleteUser() {
        when(repository.findById(userEntityId)).thenReturn(Optional.of(userEntity));

        service.delete(userEntityId);

        verify(repository, times(1)).findById(userEntityId);
        verify(repository, times(1)).delete(userEntity);
    }
}

package com.library.service;

import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.entity.User;
import com.library.exception.IdNotFoundException;
import com.library.mapper.UserMapper;
import com.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    @Autowired
    UserRepository repository;

    public User save(@Valid UserRequestDto dto) {
        User user = mapper.toEntity(dto);
        user.setRegistrationDate(LocalDate.now());
        return repository.save(user);
    }

    public UserResponseDto findById(Long id) {
        return mapper.toResponseDto(repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id)));
    }

    public List<UserResponseDto> findAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    public UserResponseDto findByName(String name) {
        return mapper.toResponseDto(repository.findByName(name));
    }

    public UserResponseDto update(Long id,  UserRequestDto dto) {
        User savedUser = repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));

        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());

        return mapper.toResponseDto(repository.save(savedUser));
    }

    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));
        repository.delete(user);
    }
}

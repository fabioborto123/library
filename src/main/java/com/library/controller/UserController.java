package com.library.controller;

import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.entity.Book;
import com.library.entity.User;
import com.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto dto) {
        User user = service.save(dto);
        return ResponseEntity.created(URI.create("/user" + user.getId())).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<UserResponseDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}

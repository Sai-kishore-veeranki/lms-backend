package com.vsk.lms.user_service.controller;

import com.vsk.lms.user_service.client.AuthClient;
import com.vsk.lms.user_service.dto.UserDto;
import com.vsk.lms.user_service.service.serviceinterfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthClient authClient;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id,
                                           @RequestHeader("Authorization") String token) {
        authClient.validateToken(token);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestHeader("Authorization") String token) {
        authClient.validateToken(token);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto dto,
                                              @RequestHeader("Authorization") String token) {
        authClient.validateToken(token);
        return ResponseEntity.ok(userService.createUser(dto));
    }
}

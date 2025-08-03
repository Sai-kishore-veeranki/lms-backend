package com.vsk.lms.auth_service.service;

import com.vsk.lms.auth_service.dto.AuthResponse;
import com.vsk.lms.auth_service.dto.LoginRequest;
import com.vsk.lms.auth_service.dto.RegisterRequest;
import com.vsk.lms.auth_service.model.AppUser;
import com.vsk.lms.auth_service.model.enums.Role;
import com.vsk.lms.auth_service.repository.UserRepository;
import com.vsk.lms.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        AppUser user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();

        userRepository.save(user);
        return "User Registered Successfully";
    }

    public boolean validateToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            AppUser user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return false;
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().name())
                    .build();

            return jwtService.isTokenValid(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

}


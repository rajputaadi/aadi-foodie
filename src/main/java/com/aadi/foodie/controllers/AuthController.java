package com.aadi.foodie.controllers;

import com.aadi.foodie.Security.JwtService;
import com.aadi.foodie.dto.JwtResponse;
import com.aadi.foodie.dto.LoginRequest;
import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        String token = jwtService.generateToken(loginRequest.getEmail());
        UserDto userDetails = userService.getUsersByEmail(loginRequest.getEmail());
        JwtResponse build = JwtResponse.builder().token(token).build();

        return ResponseEntity.ok(build);
    }
}

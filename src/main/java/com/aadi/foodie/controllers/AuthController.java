package com.aadi.foodie.controllers;

import com.aadi.foodie.Security.JwtService;
import com.aadi.foodie.dto.JwtResponse;
import com.aadi.foodie.dto.LoginRequest;
import com.aadi.foodie.dto.RefreshTokenRequest;
import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.service.UserService;
import org.springframework.http.HttpStatus;
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
        UserDto userDto = userService.getUsersByEmail(loginRequest.getEmail());


        String accessToken = jwtService.generateToken(userDto.getEmail(), true);
        String refreshToken = jwtService.generateToken(userDto.getEmail(), false);
        JwtResponse build = JwtResponse.builder().accessToken(accessToken).refreshToken(refreshToken).userDetails(userDto).build();

        return ResponseEntity.ok(build);
    }

    //api call for refresh token
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        if(!jwtService.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        if (jwtService.validateToken(refreshTokenRequest.getRefreshToken())) {
            String userIdFromToken = jwtService.getUserIdFromToken(refreshTokenRequest.getRefreshToken());
            UserDto userById = userService.getUserById(userIdFromToken);
            String accessToken = jwtService.generateToken(userById.getEmail(), true);
            String refreshToken = jwtService.generateToken(userById.getEmail(), false);
            JwtResponse response = JwtResponse.builder().accessToken(accessToken).refreshToken(refreshToken).userDetails(userById).build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
        }
    }
}

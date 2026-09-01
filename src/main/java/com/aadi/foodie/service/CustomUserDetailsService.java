package com.aadi.foodie.service;

import com.aadi.foodie.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// used only by AuthenticationManager.authenticate() during /api/v1/auth/login;
// role-based authorization on later requests comes from JwtAuthenticationFilter instead.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userService.getUsersByEmail(email);
        if (userDto == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<GrantedAuthority> authorities = userDto.getRoleEntities() == null
                ? List.of()
                : userDto.getRoleEntities().stream()
                        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                        .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(userDto.getEmail())
                .password(userDto.getPassword())
                .authorities(authorities)
                .build();
    }
}

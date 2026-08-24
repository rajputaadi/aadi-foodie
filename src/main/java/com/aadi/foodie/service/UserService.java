package com.aadi.foodie.service;

import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    Page<UserDto> getAll(Pageable pageable);

    List<UserDto> getUsersByName(String name);

    UserDto getUsersByEmail(String email);

    UserDto getUserById(String id);

    void deleteUser(String userId);
}

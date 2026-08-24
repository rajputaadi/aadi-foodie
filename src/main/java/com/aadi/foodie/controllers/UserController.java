package com.aadi.foodie.controllers;

import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create user API
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.saveUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    // Get all users API
    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    //Get single User by id API
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }


}

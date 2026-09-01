package com.aadi.foodie.service.impl;

import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.entity.RoleEntity;
import com.aadi.foodie.entity.User;
import com.aadi.foodie.exception.ResourceNotFoundException;
import com.aadi.foodie.repository.RoleRepo;
import com.aadi.foodie.repository.UserRepo;
import com.aadi.foodie.service.UserService;
import com.aadi.foodie.utils.AppConstants;
import com.aadi.foodie.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
    }

    private User convertUserDtoToUser(UserDto userDto) {
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setEmail(userDto.getEmail());
//        user.setAddress(userDto.getAddress());
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertUserToUserDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setPassword(user.getPassword());
//        userDto.setPhoneNumber(user.getPhoneNumber());
//        userDto.setEmail(user.getEmail());
//        userDto.setAddress(user.getAddress());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {

        userDto.setId(Helper.generateRandomId());
        User user = convertUserDtoToUser(userDto);
        user.setEmail(userDto.getEmail().toLowerCase());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        RoleEntity roleGuest = roleRepo.findByName(AppConstants.getRoleGuest());
        user.getRoleEntities().add(roleGuest);
        User savedUser = userRepo.save(user);
        return convertUserToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {

        Page<User> usersPage = userRepo.findAll(pageable);

        return usersPage.map(this::convertUserToUserDto);
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        List<UserDto> listOfUsers = userRepo.findByName(name).stream().map(user -> convertUserToUserDto(user)).toList();
        return listOfUsers;
    }

    @Override
    public UserDto getUsersByEmail(String email) {
        User user = userRepo.findByEmail(email.toLowerCase());
        return user != null ? convertUserToUserDto(user) : null;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertUserToUserDto(user);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user);
    }
}

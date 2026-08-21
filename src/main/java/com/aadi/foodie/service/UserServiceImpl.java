package com.aadi.foodie.service;

import com.aadi.foodie.entity.Role;
import com.aadi.foodie.entity.RoleEntity;
import com.aadi.foodie.entity.User;
import com.aadi.foodie.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public User saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        User savedEntity = userRepo.save(user);
        return savedEntity;
    }

    @Override
    public void testUserRole() {
        User user = new User();
        user.setName("Ankur");
        user.setId(UUID.randomUUID().toString());
        user.setEmail("ankur@yopmail.com");
        user.setAvailable(true);
        user.setPassword("sw323");
        user.setAddress("Pannu tower");

        RoleEntity entity1 = new RoleEntity();
        entity1.setName("ROLE_ADMIN");

        RoleEntity entity2 = new RoleEntity();
        entity2.setName("ROLE_GUEST");

        user.getRoleEntities().add(entity1);
        user.getRoleEntities().add(entity2);

        entity1.getUsers().add(user);
        entity2.getUsers().add(user);

        userRepo.save(user);




    }

    public User updateUser(User user, String userId) {
        User dbUser = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        dbUser.setName(user.getName());
        dbUser.setEmail(user.getEmail());
        dbUser.setAvailable(user.isAvailable());
        dbUser.setPassword(user.getPassword());
        dbUser.setRole(user.getRole());
        dbUser.setRestaurants(user.getRestaurants());
        dbUser.setAvailable(user.isAvailable());
        dbUser.setAddress(user.getAddress());
        dbUser.setPhoneNumber(user.getPhoneNumber());

        User updatedUser = userRepo.save(dbUser);
        return updatedUser;

    }
}

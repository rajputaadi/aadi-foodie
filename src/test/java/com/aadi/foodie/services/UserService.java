package com.aadi.foodie.services;

import com.aadi.foodie.entity.Restaurant;
import com.aadi.foodie.entity.Role;
import com.aadi.foodie.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UserService {
    @Autowired
    private com.aadi.foodie.service.UserService userService;

    @Test
    public void testSaveUser() {


//        User user = new User();
//        user.setName("Ankit");
//        user.setEmail("Ankit@yopmail.com");
//        user.setAvailable(true);
//        user.setPassword("12345");
//        user.setRole(Role.ADMIN);
//
//        // create some restaurants
//        Restaurant restaurant = new Restaurant();
//        restaurant.setId(UUID.randomUUID().toString());
//        restaurant.setName("Ankit's Restro");
//        restaurant.setAddress("Phase 5 Market");
//        restaurant.setOpen(true);
//        restaurant.setUser(user);
//
//        Restaurant restaurant1 = new Restaurant();
//        restaurant1.setId(UUID.randomUUID().toString());
//        restaurant1.setName("Ankit's second Restro");
//        restaurant1.setAddress("b2 market Mohali");
//        restaurant1.setOpen(true);
//        restaurant1.setUser(user);
//
//        user.getRestaurants().add(restaurant);
//        user.getRestaurants().add(restaurant1);
//
//        userService.saveUser(user);
//        System.out.println(" user saved");
    }


    @Test
    public void testUpdate(){
        userService.testUserRole();
    }

}

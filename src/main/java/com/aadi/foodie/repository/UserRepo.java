package com.aadi.foodie.repository;

import com.aadi.foodie.dto.UserDto;
import com.aadi.foodie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    //custom query methods
    List<User> findByName(String name);
    UserDto findByEmail(String email);
}

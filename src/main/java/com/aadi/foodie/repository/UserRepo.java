package com.aadi.foodie.repository;

import com.aadi.foodie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    //custom query methods

}

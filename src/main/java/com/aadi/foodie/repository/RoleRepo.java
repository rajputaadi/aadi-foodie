package com.aadi.foodie.repository;

import com.aadi.foodie.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);

}

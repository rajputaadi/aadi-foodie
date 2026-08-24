package com.aadi.foodie.repository;

import com.aadi.foodie.dto.RestaurantDto;
import com.aadi.foodie.entity.Restaurant;
import com.aadi.foodie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, String> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    Page<Restaurant> findByOpen(boolean flag, Pageable pageable);
}

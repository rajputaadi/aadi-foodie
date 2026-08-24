package com.aadi.foodie.service;

import com.aadi.foodie.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantService {
    RestaurantDto add(RestaurantDto restaurantDto);

    RestaurantDto update(RestaurantDto restaurantDto, String id);

    RestaurantDto delete(String id);

    RestaurantDto getRestaurantById(String id);

    Page<RestaurantDto> getAllRestaurants(Pageable pageable);

    List<RestaurantDto> searchByName(String name);

    Page<RestaurantDto> getOpenRestaurants(Pageable pageable);
}

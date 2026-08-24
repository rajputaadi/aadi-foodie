package com.aadi.foodie.service.impl;

import com.aadi.foodie.dto.RestaurantDto;
import com.aadi.foodie.entity.Restaurant;
import com.aadi.foodie.exception.ResourceNotFoundException;
import com.aadi.foodie.repository.RestaurantRepo;
import com.aadi.foodie.service.RestaurantService;
import com.aadi.foodie.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepo restaurantRepo;
    private ModelMapper modelMapper;


    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, ModelMapper modelMapper) {
        this.restaurantRepo = restaurantRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantDto add(RestaurantDto restaurantDto) {
        restaurantDto.setId(Helper.generateRandomId());
        Restaurant restaurantEntity = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant savedRestaurantEntity = restaurantRepo.save(restaurantEntity);
        RestaurantDto savedRestaurantDto = modelMapper.map(savedRestaurantEntity, RestaurantDto.class);
        return savedRestaurantDto;
    }

    @Override
    public RestaurantDto update(RestaurantDto restaurantDto, String id) {
        Restaurant dbRestaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        dbRestaurant.setName(restaurantDto.getName());
        dbRestaurant.setAddress(restaurantDto.getAddress());
        dbRestaurant.setOpenTime(restaurantDto.getOpenTime());
        dbRestaurant.setAddress(restaurantDto.getAddress());
        dbRestaurant.setOpen(restaurantDto.isOpen());
        dbRestaurant.setCloseTime(restaurantDto.getCloseTime());
        dbRestaurant.setDescription(restaurantDto.getDescription());
        dbRestaurant.setBanner(restaurantDto.getBanner());

        Restaurant savedRestaurant = restaurantRepo.save(dbRestaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);

    }

    @Override
    public RestaurantDto delete(String id) {
        Restaurant dbRestaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        restaurantRepo.delete(dbRestaurant);
        return modelMapper.map(dbRestaurant, RestaurantDto.class);
    }

    @Override
    public RestaurantDto getRestaurantById(String id) {
        Restaurant dbRestaurant = restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        return modelMapper.map(dbRestaurant, RestaurantDto.class);
    }

    @Override
    public Page<RestaurantDto> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepo.findAll(pageable);
        return restaurantPage.map(item -> modelMapper.map(item, RestaurantDto.class));
    }

    @Override
    public List<RestaurantDto> searchByName(String name) {
        return restaurantRepo.findByNameContainingIgnoreCase(name).stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                .toList();
    }

    @Override
    public Page<RestaurantDto> getOpenRestaurants(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepo.findByOpen(true, pageable);
        return restaurantPage.map(item -> modelMapper.map(item, RestaurantDto.class));
    }
}

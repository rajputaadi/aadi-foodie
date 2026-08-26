package com.aadi.foodie.controllers;

import com.aadi.foodie.dto.RestaurantDto;
import com.aadi.foodie.service.FileService;
import com.aadi.foodie.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestaurantService restaurantService;
    private FileService fileService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, FileService fileService) {
        this.restaurantService = restaurantService;
        this.fileService = fileService;
    }

    //add restaurant API
    @PostMapping
    public ResponseEntity<RestaurantDto> add(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto addedRestro = restaurantService.add(restaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRestro);
    }

    //update restaurant API
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> update(@PathVariable String restaurantId, @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.update(restaurantDto, restaurantId));
    }

    //get all restaurants
    @GetMapping
    public ResponseEntity<Page<RestaurantDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RestaurantDto> allRestaurants = restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allRestaurants);
    }

    // search restaurant by name
    @GetMapping("/search/{name}")
    public ResponseEntity<List<RestaurantDto>> getByName(@PathVariable String name) {
        List<RestaurantDto> restaurantDtos = restaurantService.searchByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDtos);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable String restaurantId) {
        RestaurantDto restaurantById = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantById);
    }

    // delete restaurant
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> delete(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.delete(restaurantId));
    }

    //api to handle banner upload
    @PostMapping("/upload-banner/{restaurantId}")
    public ResponseEntity<?> uploadFile(
            @RequestParam("banner") MultipartFile banner,
            @PathVariable String restaurantId) throws IOException {
        logger.info("Uploading banner ---->");
        logger.info("uploading " + banner.getOriginalFilename());
        logger.info("uploading " + banner.getContentType());



    }

}

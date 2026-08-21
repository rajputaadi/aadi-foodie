package com.aadi.foodie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "foodie_restaurant")
@Getter
@Setter
public class Restaurant {

    @Id
    private String id;
    private String name;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isOpen = true;

    @ManyToOne
    User user;
}

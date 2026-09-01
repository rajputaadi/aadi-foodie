package com.aadi.foodie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "foodie_users")
public class User {

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String createdAt;
    private String address;
    private String phoneNumber;
    private String createdDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isAvailable = true;   //applicable for delivery boy

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Restaurant> restaurants =  new ArrayList<>();



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roleEntities = new ArrayList<>();

    @PrePersist
    public void preSave(){
        this.createdAt = LocalDateTime.now().toString();
    }

    @PostPersist
    public void postSave(){
        System.out.println("Entity saved " + this.getName());
    }

    @PreUpdate
    public void preUpdate(){

    }


}

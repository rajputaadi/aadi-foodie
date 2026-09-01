package com.aadi.foodie.config;

import com.aadi.foodie.entity.RoleEntity;
import com.aadi.foodie.repository.RoleRepo;
import com.aadi.foodie.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // without this, mapping a DTO with a null list field (e.g. UserDto.roleEntities
        // on registration) overwrites the entity's default `new ArrayList<>()` with null
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // seeds the two roles the app relies on; without this, RoleRepo.findByName(...)
    // returns null and every signup fails trying to attach a null role
    @Bean
    public CommandLineRunner seedRoles(RoleRepo roleRepo) {
        return args -> {
            for (String roleName : new String[]{AppConstants.getRoleAdmin(), AppConstants.getRoleGuest()}) {
                if (roleRepo.findByName(roleName) == null) {
                    RoleEntity role = new RoleEntity();
                    role.setName(roleName);
                    roleRepo.save(role);
                }
            }
        };
    }
}

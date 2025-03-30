package com.project.dto;

import com.project.entities.enus.DriverStatus;
import com.project.entities.enus.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long id;

    private  String name;

    private String email;

    private Set<Role> roles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DriverStatus driverStatus;

    @JsonIgnore
    private  String vehicleId;

}



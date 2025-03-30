package com.project.dto;

import com.project.entities.enus.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignupDto {

    private String name;

    private String email;

    private String password;

    private Set<Role> roles;
}

package com.project.service;

import com.project.dto.DriverDto;
import com.project.dto.SignupDto;
import com.project.dto.UserDto;

public interface AuthService {

    String []login(String email, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId,String vehicleId);

    String refreshToken(String refreshToken);

//    UserDto getAllDetails(int driverId);
}

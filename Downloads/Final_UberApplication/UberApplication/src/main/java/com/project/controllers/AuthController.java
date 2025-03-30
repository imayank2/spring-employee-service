package com.project.controllers;

import com.project.advices.ApiResponse;
import com.project.dto.*;
import com.project.entities.User;
import com.project.repository.UserRepository;
import com.project.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@RequestBody SignupDto signupDto) {
        UserDto userDto = authService.signup(signupDto);
        ApiResponse<UserDto> apiResponse =
                new ApiResponse<>(true, "User registered successfully", userDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onboardNewDriver(
            @PathVariable Long userId,
            @RequestBody OnboardDriverDto onboardDriverDto) {
        DriverDto driverDto = authService.onboardNewDriver(userId, String.valueOf(onboardDriverDto));
        return new ResponseEntity<>(driverDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse httpServletResponse) {
        String[] tokens = authService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );

        LoginResponseDto loginResponseDto = new LoginResponseDto(tokens[0]);
        ApiResponse<LoginResponseDto> response =
                new ApiResponse<>(true, "Login successful", loginResponseDto);

        Cookie cookie = new Cookie("refreshToken", tokens[1]);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found"));

        String accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userRepository.findAll();
        ApiResponse<List<User>> response =
                new ApiResponse<>(true, "Users retrieved successfully", users);
        return ResponseEntity.ok(response);
    }
}
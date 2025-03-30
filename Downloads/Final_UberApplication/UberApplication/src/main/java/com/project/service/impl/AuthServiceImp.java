package com.project.service.impl;

import com.project.dto.DriverDto;
import com.project.dto.SignupDto;
import com.project.dto.UserDto;
import com.project.entities.Driver;
import com.project.entities.User;
import com.project.entities.enus.DriverStatus;
import com.project.entities.enus.Role;
import com.project.exception.ResourceNotFoundException;
import com.project.exception.RuntineConflictException;
import com.project.repository.UserRepository;
import com.project.security.JWTService;
import com.project.service.AuthService;
import com.project.service.DriverService;
import com.project.service.RiderService;
import com.project.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.project.entities.enus.Role.DRIVER;

@RequiredArgsConstructor
@Service

public class AuthServiceImp implements AuthService {

    private final ModelMapper modelMapper;

    private  final UserRepository userRepository;

    private  final RiderService riderService;

    private final WalletService walletService;

    private final DriverService driverService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    @Override
    public String[] login(String email, String password) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        User user=(User) authentication.getPrincipal();

        String accessToken=jwtService.generateAccessToken(user);

        String refreshToken= jwtService.generateRefreshToken(user);

        String []tokens={accessToken,refreshToken};

        //line new adding
        user.setAccessToken(accessToken);

        userRepository.save(user);

        return tokens;
    }

    @Override
    @Transactional
    public UserDto  signup(SignupDto signupDto) {

        User user= userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(user !=null)
            throw new RuntineConflictException(
                    "Can not signup User already exists with this main"
            + signupDto.getEmail());

        User mappedUser= modelMapper.map(signupDto,User.class);

         if(mappedUser.getRoles()==null) {
             mappedUser.setRoles(Set.of(Role.RIDER));
          }

        else  if(mappedUser.getRoles().contains(DRIVER)){
                 mappedUser.setDriverStatus(DriverStatus.CONFIRM);
            }

        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));

        User saveUser=userRepository.save(mappedUser);

        //create user related entity

        riderService.createNewRider(saveUser);


        walletService.createNewWallet(saveUser);

        return modelMapper.map(saveUser,UserDto.class);
    }



    @Override
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user=userRepository.findById(userId).orElseThrow(()->new
                ResourceNotFoundException("User not found with this "+userId));

        if(user.getRoles().contains(DRIVER))
            throw new RuntimeException("user with id"+userId+"is already a driver");

        Driver createDriver=Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);
        Driver saveDriver=driverService.createNewDriver(createDriver);

        return modelMapper.map(saveDriver,DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId=jwtService.getUserIdFromToken(refreshToken);

        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User Not found with user id "+userId));

        return jwtService.generateAccessToken(user);
    }
}

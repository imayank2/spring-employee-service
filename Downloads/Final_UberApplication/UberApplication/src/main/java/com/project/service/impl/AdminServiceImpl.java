package com.project.service.impl;

import com.project.dto.DriverDto;
import com.project.dto.UserDto;
import com.project.entities.Driver;
import com.project.entities.User;
import com.project.entities.enus.DriverStatus;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.DriverRepository;
import com.project.repository.UserRepository;
import com.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private  final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final DriverRepository driverRepository;
    @Override
    public UserDto getAllDetails(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with this id"+userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public DriverDto getUpdateStatus(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with this id"+ userId));
        if(user.getDriverStatus().equals(DriverStatus.PENDING)){
            user.setDriverStatus(DriverStatus.CONFIRM);
        }

        return null;
    }
}

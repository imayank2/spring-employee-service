package com.project.service;

import com.project.dto.DriverDto;
import com.project.dto.RideDto;
import com.project.dto.RideRequestDto;
import com.project.dto.RiderDto;
import com.project.entities.Rider;
import com.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

 RideRequestDto requestRide(RideRequestDto rideRequestDto);

 RideDto cancelRide(Long rideId);

 DriverDto rateDriver(Long rideId, Integer rating);

 RiderDto getMyProfile();

 Page<RideDto> getAllMyRides(PageRequest pageRequest);

 Rider createNewRider(User user);

 Rider getCurrentRider();


}
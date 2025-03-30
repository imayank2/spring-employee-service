package com.project.service;

import com.project.dto.DriverDto;
import com.project.dto.RideDto;
import com.project.dto.RiderDto;
import com.project.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

  RideDto acceptRide(Long rideId);

  RideDto cancelRide(Long rideId);

  RideDto startRide(Long rideId,String otp);

  RideDto endRide(Long rideId);

  RiderDto rateRider(Long riderId, Integer rating);

  DriverDto getMyProfile();

  Page<RideDto> getAllMyRides(PageRequest pageRequest);

 Driver getCurrentDriver();

 Driver updateDriverAvailability(Driver driver , boolean availability);

 Driver createNewDriver(Driver driver);


    DriverDto makeUserDriver(Long userId, DriverDto driverDto);
}

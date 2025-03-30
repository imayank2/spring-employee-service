package com.project.service.impl;

import com.project.advices.ApiError;
import com.project.advices.ApiResponse;
import com.project.dto.DriverDto;
import com.project.dto.RideDto;
import com.project.dto.RiderDto;
import com.project.entities.Driver;
import com.project.entities.Ride;
import com.project.entities.RideRequest;
import com.project.entities.User;
import com.project.entities.enus.RideRequestStatus;
import com.project.entities.enus.RideStatus;
import com.project.entities.enus.Role;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.DriverRepository;
import com.project.repository.UserRepository;
import com.project.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverServiceImp implements DriverService {

    private  final RideRequestService rideRequestService;

    private final DriverRepository driverRepository;

    private final RideService rideService;

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestedId) {

        RideRequest rideRequest=rideRequestService.findRideRequestById(rideRequestedId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException(
                    new ApiResponse<>(new ApiError(
                            "Ride request is not in pending status. Current status: "
                                    + rideRequest.getRideRequestStatus(),
                            HttpStatus.BAD_REQUEST
                    )).toString()
            );
        }

        Driver currentDriver=getCurrentDriver();

        if(!currentDriver.getAvailable()){
            new ApiResponse<>(new ApiError(
                    "Driver can not accent the ride ",
                    HttpStatus.BAD_REQUEST
            )).toString();
        }



        Driver saveDriver= this.updateDriverAvailability(currentDriver,false);

        Ride ride= rideService.createNewRide(rideRequest,saveDriver);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride=rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a " +
                    "ride as he has not accepted earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status"
                    +ride.getRideStatus());
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELLED);

        this.updateDriverAvailability(driver,true);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override

    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a " +
                    "ride as he has not accepted earlier");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: "+ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid "+otp);
        }

        rideService.updateRideStatus(ride,RideStatus.ONGOING);

        ride.setStartedAt(LocalDateTime.now());

        Ride SavedRide=rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(SavedRide);

        ratingService.createNewRating(SavedRide);

        return modelMapper.map(SavedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended, status: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {

        Ride ride=rideService.getRideById(rideId);

        Driver driver=getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not rate of the owner of this drive");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended hence can not start rating");
        }

        return   ratingService.rateRider(ride,rating);


    }

    @Override
    public DriverDto getMyProfile() {

        Driver currentDriver=getCurrentDriver();

        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Driver currentDriver=getCurrentDriver();

        return rideService.getAllRidesOfDriver(currentDriver,pageRequest)
                .map(ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(()->
                new ResourceNotFoundException("Driver not associated user with id"+user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {

        driver.setAvailable(available);

        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {

        return driverRepository.save(driver);
    }

    private final UserRepository userRepository;
    @Transactional
    public DriverDto makeUserDriver(Long userId, DriverDto driverDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Optional<Driver> existingDriver = driverRepository.findByUserId(userId);
        if (existingDriver.isPresent()) {
            throw new RuntimeException("User is already a driver!");
        }

        if (!user.getRoles().contains(Role.DRIVER)) {
            user.getRoles().add(Role.DRIVER);
            userRepository.save(user);
        }

        Driver driver = modelMapper.map(driverDto, Driver.class);
        driver.setUser(user);
        driver = driverRepository.save(driver);

        return modelMapper.map(driver, DriverDto.class);
    }


}

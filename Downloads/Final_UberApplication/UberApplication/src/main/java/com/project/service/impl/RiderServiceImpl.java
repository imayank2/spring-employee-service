package com.project.service.impl;

import com.project.dto.DriverDto;
import com.project.dto.RideDto;
import com.project.dto.RideRequestDto;
import com.project.dto.RiderDto;
import com.project.entities.*;
import com.project.entities.enus.RideRequestStatus;
import com.project.entities.enus.RideStatus;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.RideRequestRepository;
import com.project.repository.RiderRepository;
import com.project.service.DriverService;
import com.project.service.RatingService;
import com.project.service.RideService;
import com.project.service.RiderService;
import com.project.stratergies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;

    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        log.info("Ride request: {}", rideRequest);

        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager
                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

        // TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!ride.getRider().equals(rider)) {
            throw new RuntimeException("Rider does not own this ride with id: " + rideId);
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: " + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride=rideService.getRideById(rideId);

        Rider rider=getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Driver is not rate of the owner of this drive");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended hence can not start rating "
                    +ride.getRideStatus());
        }

        return   ratingService.rateDriver(ride,rating);

    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        // TODO: Implement Spring Security

        User user= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated  with id: " +user.getId()
        ));
    }
}

package com.project.stratergies.Imp;
import com.project.entities.Driver;
import com.project.entities.RideRequest;
import com.project.repository.DriverRepository;
import com.project.stratergies.DriverMatchingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRated implements DriverMatchingStrategies {

   private  final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findMatchingTenNearestDriver(rideRequest.getPickupLocation());
    }
}

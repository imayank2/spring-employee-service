package com.project.stratergies.Imp;
import com.project.entities.RideRequest;
import com.project.service.DistanceService;
import com.project.stratergies.RideFareCalculatationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor

public class RiderFairDefaultFareConfigrationStatergy implements RideFareCalculatationStrategy {
    private  final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {

        double distance=distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());

        System.out.println(distance);

        return distance*RIDE_FAIR_MULTIPLIER;

    }
}

package com.project.stratergies;

import com.project.entities.RideRequest;

public interface RideFareCalculatationStrategy {
    int RIDE_FAIR_MULTIPLIER=10;
    double calculateFare(RideRequest rideRequest);

}

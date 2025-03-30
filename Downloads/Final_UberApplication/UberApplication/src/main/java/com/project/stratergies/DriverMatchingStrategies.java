package com.project.stratergies;

import com.project.entities.Driver;
import com.project.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategies {

     List<Driver> findMatchingDriver(RideRequest rideRequest);
}

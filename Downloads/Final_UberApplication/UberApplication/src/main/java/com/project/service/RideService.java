package com.project.service;

import com.project.entities.Driver;
import com.project.entities.Ride;
import com.project.entities.RideRequest;
import com.project.entities.Rider;
import com.project.entities.enus.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



public interface RideService {

    Ride getRideById(Long rideId);


    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}

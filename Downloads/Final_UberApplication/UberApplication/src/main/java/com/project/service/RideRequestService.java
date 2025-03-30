package com.project.service;


import com.project.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);


}


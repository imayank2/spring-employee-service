package com.project.service;

import com.project.dto.DriverDto;
import com.project.dto.RiderDto;
import com.project.entities.Ride;


public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);

}

package com.project.repository;

import com.project.entities.Driver;
import com.project.entities.Rating;
import com.project.entities.Ride;
import com.project.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
package com.project.repository;

import com.project.entities.Driver;
import com.project.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    // Query to find the 10 nearest available drivers ordered by distance
    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 100000) " +  // Within 100km radius
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<Driver> findMatchingTenNearestDriver(Point pickupLocation);

    // Query to find 10 nearby available drivers ordered by highest rating
    @Query(value = "SELECT d.* " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +  // Within 15km radius
            "ORDER BY d.rating DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Driver> findTenNearbyTopRatedDriver(Point pickupLocation);

    Optional<Driver> findByUser(User user);

    Optional<Driver> findByUserId(Long userId);
}

package com.project.stratergies;

import com.project.stratergies.Imp.DriverMatchingHighestRated;
import com.project.stratergies.Imp.DriverMatchingNearestDriver;
import com.project.stratergies.Imp.RideFareSurgePricingStrategy;
import com.project.stratergies.Imp.RiderFairDefaultFareConfigrationStatergy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@RequiredArgsConstructor
@Component
public class RideStrategyManager {

    private final DriverMatchingHighestRated driverMatchingHighestRated;

    private final DriverMatchingNearestDriver nearestDriver;

    private final RideFareSurgePricingStrategy surgePricingStrategy;

    private final RiderFairDefaultFareConfigrationStatergy defaultFareConfigrationStatergy;

    public DriverMatchingStrategies driverMatchingStrategy(Double riderRating){
        if(riderRating>=4.8){
            return driverMatchingHighestRated;
        }
        else {
            return nearestDriver;
        }

    }

    public RideFareCalculatationStrategy rideFareCalculationStrategy(){

        //6pm to 9pm

        LocalTime surgeStartTime=LocalTime.of(18,0);

        LocalTime surgeEndTime=LocalTime.of(21,0);

        LocalTime currentTime=LocalTime.now();

        boolean isSurgeTime= currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
         return surgePricingStrategy;
        }
        else {
         return defaultFareConfigrationStatergy;

        }
    }

}

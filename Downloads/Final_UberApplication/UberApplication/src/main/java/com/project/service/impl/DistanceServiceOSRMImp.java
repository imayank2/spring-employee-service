package com.project.service.impl;
import com.project.service.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImp implements DistanceService {

    private static final String OSRM_API = "https://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point dest) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException("Source or Destination cannot be null");
        }

        try {
            String uri = src.getX() + "," + src.getY() + ";" + dest.getX() + "," + dest.getY();
            OSRMResponseDto responseDto = RestClient.
                    builder()
                    .baseUrl(OSRM_API)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting data from osrm" + e.getMessage());
        }
    }


}
    @Data
    class OSRMResponseDto {
        private List<OSRMRoute> routes;
    }
    @Data
    class OSRMRoute {
        private Double distance;
    }


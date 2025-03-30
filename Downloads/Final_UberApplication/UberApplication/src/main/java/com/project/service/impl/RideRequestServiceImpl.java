package com.project.service.impl;
import com.project.advices.ApiError;
import com.project.advices.ApiResponse;
import com.project.entities.RideRequest;
import com.project.repository.RideRequestRepository;
import com.project.service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new RuntimeException(
                        new ApiResponse<>(new ApiError(
                                "Ride request not found with this id: "
                                        + rideRequestId,
                                HttpStatus.BAD_REQUEST
                        )).toString()
                ));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId()).orElseThrow(() ->
                new RuntimeException(
                        new ApiResponse<>(new ApiError(
                                "Ride request not found with this id: "
                                        + rideRequest.getId(),
                                HttpStatus.BAD_REQUEST
                        )).toString()
                )
        );
        rideRequestRepository.save(rideRequest);

    }

}


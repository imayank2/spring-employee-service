package com.project.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ApiResponse<T> {

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;


    public ApiResponse(){
        this.timeStamp=LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }


    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }

    // Constructor for responses with both data and error
    public ApiResponse(boolean success, String message, T data) {
        this();
        if (!success) {
            this.error = new ApiError(message);
        }
        this.data = data;
    }

}

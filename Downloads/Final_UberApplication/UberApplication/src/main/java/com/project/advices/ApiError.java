package com.project.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private String message;

    private HttpStatus status;

    private List<String> subError;

    public ApiError(String message) {
        this.message = message;
    }

    // Constructor to set the message and status
    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

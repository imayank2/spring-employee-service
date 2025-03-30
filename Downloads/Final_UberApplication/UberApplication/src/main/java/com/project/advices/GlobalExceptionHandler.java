package com.project.advices;
import com.project.exception.ResourceNotFoundException;
import com.project.exception.RuntineConflictException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiError apiError= ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }



    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }



    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }



    @ExceptionHandler(RuntineConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(RuntineConflictException exception) {
        ApiError apiError= ApiError
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception exception) {
        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred: " + exception.getMessage())
                .build();
        return buidErrorResponseEntity(apiError);
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationError(MethodArgumentNotValidException exception){
       List<String> errors= exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage() )
                .collect(Collectors.toList());

        ApiError apiError = ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation field")
                .subError(errors)
                .build();
        return buidErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buidErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }


}

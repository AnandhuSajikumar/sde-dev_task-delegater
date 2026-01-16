package com.anandhu.sde_dev.exception;


import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        return new ApiError(
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError illegalStateException(IllegalStateException ex, HttpServletRequest request){
        return new ApiError(
                409,
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI()
        );

    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleVersion(OptimisticLockException ex, HttpServletRequest request){
        return new ApiError(
                409,
                "CONCURRENT_UPDATE",
                "Task was modified by another request. Please retry.",
                request.getRequestURI()
        );

    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleVersion(IllegalArgumentException ex, HttpServletRequest request) {
        return new ApiError(
                400,
                "CONCURRENT_UPDATE",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiError handleVersion(UsernameNotFoundException ex, HttpServletRequest request){
//        return new ApiError(
//                404,
//                "NOT_FOUND",
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//    }
}

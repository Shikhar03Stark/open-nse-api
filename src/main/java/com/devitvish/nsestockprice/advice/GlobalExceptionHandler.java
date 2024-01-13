package com.devitvish.nsestockprice.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.error.NseBaseError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NseBaseError.class})
    public ResponseEntity<ErrorResponse> handleCustomError(NseBaseError baseError){
        int httpStatusCode = 500;
        if(baseError.getExceptionNature() == BaseExceptionNature.FUNCTIONAL){
            httpStatusCode = 400;
        }
        log.error("Error encountered httpStatus={} message={}", httpStatusCode, baseError.getMessage());
        final ErrorResponse response = ErrorResponse
            .builder(baseError, HttpStatusCode.valueOf(httpStatusCode), baseError.getMessage()).build();
        
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(httpStatusCode));
    }
}

package com.devitvish.nsestockprice.exception.error;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseBadRequestError extends NseBaseError {
    
    private final String message;

    private NseBadRequestError(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature,
            Throwable throwable, String message) {
        super(symbol, operationType, baseExceptionNature, throwable);
        this.message = message;
    }

    public NseBadRequestError(String symbol, OperationType operationType,  String message, Throwable throwable){
        this(symbol, operationType, BaseExceptionNature.FUNCTIONAL, throwable, message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}

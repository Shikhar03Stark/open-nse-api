package com.devitvish.nsestockprice.exception.error;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseResourceNotFoundError extends NseBaseError {

    private NseResourceNotFoundError(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature,
            Throwable throwable) {
        super(symbol, operationType, baseExceptionNature, throwable);
    }

    public NseResourceNotFoundError(String symbol, OperationType operationType, Throwable throwable){
        this(symbol, operationType, BaseExceptionNature.FUNCTIONAL, throwable);
    }

    @Override
    public String getMessage() {
        return String.format("The resource %s was not found on NSE server", symbol);
    }

}

package com.devitvish.nsestockprice.exception.error;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseInternalServerError extends NseBaseError {

    public NseInternalServerError(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature, Throwable throwable) {
        super(symbol, operationType, baseExceptionNature, throwable);
    }

    @Override
    public String getMessage() {
        return "An internal server error was encountered. Please try again later";
    }

}

package com.devitvish.nsestockprice.exception.error;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseAPIBlankResponseError extends NseBaseError {

    private NseAPIBlankResponseError(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature,
            Throwable throwable) {
        super(symbol, operationType, baseExceptionNature, throwable);
    }

    public NseAPIBlankResponseError(String symbol, OperationType operationType, Throwable cause){
        this(symbol, operationType, BaseExceptionNature.OPERATIONAL, cause);
    }

    @Override
    public String getMessage() {
        return String.format("The NSE server is not responding to the call for the symbol %s. Try again later", this.symbol);
    }

}

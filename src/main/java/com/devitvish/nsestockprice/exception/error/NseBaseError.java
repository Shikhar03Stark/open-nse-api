package com.devitvish.nsestockprice.exception.error;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public abstract class NseBaseError extends RuntimeException{
    protected final String symbol;
    protected final OperationType operationType;
    protected final BaseExceptionNature exceptionNature;
    protected final Throwable cause;

    public NseBaseError(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature, Throwable throwable){
        super(String.format("symbol=%s operationType=%s exceptionNature=%s", symbol, operationType, baseExceptionNature), throwable);
        this.symbol = symbol;
        this.operationType = operationType;
        this.exceptionNature = baseExceptionNature;
        this.cause = throwable;
    }
}

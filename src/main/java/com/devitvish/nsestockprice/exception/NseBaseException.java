package com.devitvish.nsestockprice.exception;

import lombok.Getter;

@Getter
public abstract class NseBaseException extends Exception{
    protected final String symbol;
    protected final OperationType operationType;
    protected final BaseExceptionNature exceptionNature;
    protected final Throwable throwable;


    protected NseBaseException(String symbol, OperationType operationType, BaseExceptionNature baseExceptionNature, Throwable throwable){
        super(String.format("symbol=%s operationType=%s exceptionNature=%s", symbol, operationType, baseExceptionNature), throwable);
        this.symbol = symbol;
        this.operationType = operationType;
        this.exceptionNature = baseExceptionNature;
        this.throwable = throwable;
    }
}

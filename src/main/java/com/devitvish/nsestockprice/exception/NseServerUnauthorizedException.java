package com.devitvish.nsestockprice.exception;

import lombok.Getter;

@Getter
public class NseServerUnauthorizedException extends NseBaseException {

    private final String endpoint;

    public NseServerUnauthorizedException(String symbol, String endpoint, OperationType operationType){
        this(symbol, endpoint, operationType, null);
    }

    public NseServerUnauthorizedException(String symbol, String endpoint, OperationType operationType, Throwable throwable){
        super(symbol, operationType, BaseExceptionNature.OPERATIONAL, throwable);
        this.endpoint = endpoint;
    }

    @Override
    public String getMessage() {
        final String msg = String.format("Authroization Failure endpoint=%s, operationType=%s", endpoint, this.operationType);
        return msg;
    }

    
}

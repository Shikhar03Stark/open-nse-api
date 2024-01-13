package com.devitvish.nsestockprice.exception;

import lombok.Getter;

@Getter
public class NseStockDoesNotExistException extends NseBaseException {

    private NseStockDoesNotExistException(String symbol, OperationType operationType, BaseExceptionNature exceptionNature, Throwable throwable) {
        super(symbol, operationType, exceptionNature, throwable);
    }

    public NseStockDoesNotExistException(String symbol){
        this(symbol, OperationType.GET_QUOTE, BaseExceptionNature.FUNCTIONAL, null);
    }


    public NseStockDoesNotExistException(String symbol, Throwable throwable){
        this(symbol, OperationType.GET_QUOTE, BaseExceptionNature.FUNCTIONAL, throwable);
    }

    @Override
    public String getMessage() {
        final String msg = String.format("The symbol %s does not exists in the NSE records", this.symbol);
        return msg;
    }

}

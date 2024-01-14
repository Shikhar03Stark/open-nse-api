package com.devitvish.nsestockprice.exception.error;

import java.time.LocalDate;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseInvalidHistoricalDataError extends NseBaseError {
    private final LocalDate from;
    private final LocalDate to;


    private NseInvalidHistoricalDataError(String symbol, LocalDate from, LocalDate to, OperationType operationType,
            BaseExceptionNature baseExceptionNature, Throwable throwable) {
        super(symbol, operationType, baseExceptionNature, throwable);
        this.from = from;
        this.to = to;
    }

    public NseInvalidHistoricalDataError(String symbol, LocalDate from, LocalDate to, OperationType operationType){
        this(symbol, from, to, operationType, BaseExceptionNature.FUNCTIONAL, null);
    }

    @Override
    public String getMessage() {
        final String fromDate = from.toString();
        final String toDate = to.toString();
        return String.format("The symbol %s not found or invalid historical time window from %s and to %s", this.symbol, fromDate, toDate);
    }



}

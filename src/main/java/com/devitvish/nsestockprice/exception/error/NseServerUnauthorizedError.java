package com.devitvish.nsestockprice.exception.error;

import java.util.Objects;

import org.springframework.util.StringUtils;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.OperationType;

import lombok.Getter;

@Getter
public class NseServerUnauthorizedError extends NseBaseError {

    private final String endpoint;

    public NseServerUnauthorizedError(String symbol, String endpoint, OperationType operationType){
        this(symbol, endpoint, operationType, null);
    }

    public NseServerUnauthorizedError(String symbol, String endpoint, OperationType operationType, Throwable throwable){
        super(symbol, operationType, BaseExceptionNature.OPERATIONAL, throwable);
        this.endpoint = endpoint;
    }

    @Override
    public String getMessage() {
        if(StringUtils.hasText(symbol) && Objects.nonNull(operationType)){
            return String.format("Authroization Failure endpoint=%s, operationType=%s", endpoint, this.operationType);
        }
        return "Failed to Authorize the request";
    }

    
}

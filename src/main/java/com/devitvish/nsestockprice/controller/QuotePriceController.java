package com.devitvish.nsestockprice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.exception.OperationType;
import com.devitvish.nsestockprice.exception.error.NseBadRequestError;
import com.devitvish.nsestockprice.exception.error.NseResourceNotFoundError;
import com.devitvish.nsestockprice.resource.Stock;
import com.devitvish.nsestockprice.service.QuoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class QuotePriceController {

    private final QuoteService quoteService;

    @GetMapping(path = "/quote/{symbol}")
    public HttpEntity<Stock> getStockQuote(@PathVariable(name = "symbol") final String symbol){
        
        if(symbol.length() == 0){
            throw new NseBadRequestError(symbol, OperationType.GET_QUOTE, "The symbol item can not be empty", null);
        }
        log.info("request for symbol={}", symbol);

        try {
            final Stock stock = quoteService.getQuote(symbol);
            return ResponseEntity.ok().body(stock);
        } catch (NseStockDoesNotExistException e) {
            
            throw new NseResourceNotFoundError(symbol, OperationType.GET_QUOTE, e);
        }
    }
}

package com.devitvish.nsestockprice.service;

import org.springframework.cache.annotation.Cacheable;

import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.resource.Stock;

public interface QuoteService {
    
    @Cacheable(cacheNames = {"StockQuoteCache"}, unless = "#result == null")
    Stock getQuote(String stockSymbol) throws NseStockDoesNotExistException;
}

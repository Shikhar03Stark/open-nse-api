package com.devitvish.nsestockprice.service;

import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.resource.Stock;

public interface QuoteService {
    Stock getQuote(String stockSymbol) throws NseStockDoesNotExistException;
}

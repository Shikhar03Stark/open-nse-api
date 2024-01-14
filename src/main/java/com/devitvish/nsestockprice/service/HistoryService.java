package com.devitvish.nsestockprice.service;

import java.time.LocalDate;

import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.resource.HistoricalPrice;

public interface HistoryService {
    HistoricalPrice getDailyHistorical(String stockSymbol, LocalDate from, LocalDate to) throws NseStockDoesNotExistException;
    HistoricalPrice getDailyHistorical(String stockSymbol, LocalDate from) throws NseStockDoesNotExistException;
    HistoricalPrice getDailyHistorical(String stockSymbol) throws NseStockDoesNotExistException;

}

package com.devitvish.nsestockprice.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devitvish.nsestockprice.exception.BaseExceptionNature;
import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.exception.OperationType;
import com.devitvish.nsestockprice.exception.error.NseAPIBlankResponseError;
import com.devitvish.nsestockprice.exception.error.NseInternalServerError;
import com.devitvish.nsestockprice.exception.error.NseInvalidHistoricalDataError;
import com.devitvish.nsestockprice.network.FetchManager;
import com.devitvish.nsestockprice.resource.HistoricalPrice;
import com.devitvish.nsestockprice.resource.Stock;
import com.devitvish.nsestockprice.service.QuoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final FetchManager httpFetchManager;

    private URI getQuoteURI(String symbol) {
        return URI.create(String.format("https://www.nseindia.com/api/quote-equity?symbol=%s", symbol));
    }

    @Override
    public Stock getQuote(String stockSymbol) throws NseStockDoesNotExistException {
        final URI quoteUri = getQuoteURI(stockSymbol);
        log.info("requesting quoteUri={}", quoteUri.toString());
        try {
            final Optional<HttpResponse<String>> optionalResponse = httpFetchManager.getFrom(quoteUri);
            final HttpResponse<String> response = optionalResponse
                    .orElseThrow(() -> new NseAPIBlankResponseError(stockSymbol, OperationType.CALL_NSE_API, null));

            final Optional<Stock> optionalStock = httpFetchManager.unmarshal(response.body(), Stock.class);

            return optionalStock
                .filter(stock -> Objects.nonNull(stock.getSymbol()))
                .orElseThrow(() -> new NseStockDoesNotExistException(stockSymbol));

            
        } catch (IOException | InterruptedException e) {
            log.error("Error making api call to nse server", e);
            throw new NseInternalServerError(stockSymbol, OperationType.GET_QUOTE, BaseExceptionNature.OPERATIONAL, e);
        }
    }

}

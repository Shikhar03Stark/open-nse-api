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
import com.devitvish.nsestockprice.service.HistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final FetchManager httpFetchManager;

    private URI getDailyHistoryURI(String symbol, LocalDate from, LocalDate to) {
        final String fromDate = from.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        final String toDate = to.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return URI.create(String.format("https://www.nseindia.com/api/historical/cm/equity?series=[%%22EQ%%22]&symbol=%s&from=%s&to=%s", symbol, fromDate, toDate));
    }

    @Override
    public HistoricalPrice getDailyHistorical(String stockSymbol, LocalDate from, LocalDate to) throws NseStockDoesNotExistException {
        final URI historicalDataURI = getDailyHistoryURI(stockSymbol, from, to);

        try {
            Optional<HttpResponse<String>> optionalResponse = httpFetchManager.getFrom(historicalDataURI);
            final HttpResponse<String> response = optionalResponse
                    .orElseThrow(() -> new NseAPIBlankResponseError(stockSymbol, OperationType.CALL_NSE_API, null));

            final Optional<HistoricalPrice> optionalHistorical = httpFetchManager.unmarshal(response.body(),
                    HistoricalPrice.class);

            return optionalHistorical
                    .filter(historical -> Objects.nonNull(historical.getDayHistory()) && !historical.getDayHistory().isEmpty())
                    .orElseThrow(
                            () -> new NseInvalidHistoricalDataError(stockSymbol, from, to, OperationType.GET_HISTORY));

        } catch (IOException | InterruptedException e) {
            log.error("Error making api call to nse server", e);
            throw new NseInternalServerError(stockSymbol, OperationType.GET_QUOTE, BaseExceptionNature.OPERATIONAL, e);
        }
    }

    @Override
    public HistoricalPrice getDailyHistorical(String stockSymbol, LocalDate from) throws NseStockDoesNotExistException {
        LocalDate to = LocalDate.now();
        return getDailyHistorical(stockSymbol, from, to);
    }

    @Override
    public HistoricalPrice getDailyHistorical(String stockSymbol) throws NseStockDoesNotExistException {
        LocalDate to = LocalDate.now();
        LocalDate from = to.plusDays(-7);
        return getDailyHistorical(stockSymbol, from, to);
    }
}

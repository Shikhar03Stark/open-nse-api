package com.devitvish.nsestockprice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devitvish.nsestockprice.exception.NseStockDoesNotExistException;
import com.devitvish.nsestockprice.exception.OperationType;
import com.devitvish.nsestockprice.exception.error.NseBadRequestError;
import com.devitvish.nsestockprice.exception.error.NseResourceNotFoundError;
import com.devitvish.nsestockprice.resource.HistoricalPrice;
import com.devitvish.nsestockprice.service.HistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping(path = "/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
public class HistoricalPriceController {

    private final HistoryService historyService;

    @GetMapping(path = "/historical/{symbol}")
    public HttpEntity<HistoricalPrice> getMethodName(@PathVariable(name = "symbol") String symbol,
            @RequestParam(name = "from") Optional<String> optionalFrom,
            @RequestParam(name = "to") Optional<String> optionalTo) {

        try {
            final LocalDate toDate = optionalTo
                    .map(to -> LocalDate.parse(to, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .orElse(LocalDate.now());

            final LocalDate fromDate = optionalFrom
                    .map(from -> LocalDate.parse(from, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .filter(fdate -> fdate.isBefore(toDate))
                    .orElse(toDate.minusDays(7));

            HistoricalPrice historicalPrice = historyService.getDailyHistorical(symbol, fromDate, toDate);
            return ResponseEntity.ok(historicalPrice);
        } catch (NseStockDoesNotExistException e) {
            throw new NseResourceNotFoundError(symbol, OperationType.GET_HISTORY, e);
        } catch (DateTimeParseException e) {
            throw new NseBadRequestError(symbol, OperationType.GET_HISTORY, e.getMessage(), e);
        }
    }
}

package com.devitvish.nsestockprice.resource;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DaySummary {

    @JsonAlias("CH_SYMBOL")
    private String symbol;

    @JsonAlias("CH_OPENING_PRICE")
    private Double openingValue;

    @JsonAlias("CH_CLOSING_PRICE")
    private Double closeValue;

    @JsonAlias("CH_TRADE_HIGH_PRICE")
    private Double highValue;

    @JsonAlias("CH_TRADE_LOW_PRICE")
    private Double lowValue;

    @JsonAlias("CH_TOT_TRADED_VAL")
    private Double totalTradedValue;

    @JsonAlias("CH_TOT_TRADED_QTY")
    private Long totalTradedQuantity;

    @JsonAlias("CH_TIMESTAMP")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

}

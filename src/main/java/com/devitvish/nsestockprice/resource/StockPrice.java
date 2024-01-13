package com.devitvish.nsestockprice.resource;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice {
    private Double value;
    private Double change;
    private Double percentChange;
    private Double previousCloseValue;
    private Double openValue;
    private Double closeValue;
    private LocalDateTime timeOfValue;
}

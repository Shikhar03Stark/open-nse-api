package com.devitvish.nsestockprice.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPrice {
    @JsonAlias("data")
    private List<DaySummary> dayHistory;
}

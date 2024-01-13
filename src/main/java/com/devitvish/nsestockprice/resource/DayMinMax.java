package com.devitvish.nsestockprice.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayMinMax {
    private Double minValue;
    private Double maxValue;
}

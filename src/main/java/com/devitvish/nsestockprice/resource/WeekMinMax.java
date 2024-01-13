package com.devitvish.nsestockprice.resource;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekMinMax {
    private Double minValue;
    private LocalDate minValueDate;
    private Double maxValue;
    private LocalDate maxValueDate;
}

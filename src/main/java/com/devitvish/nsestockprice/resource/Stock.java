package com.devitvish.nsestockprice.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private String symbol;
    private String companyName;
    private LocalDate listingDate;
    private Long issuedSize;
    private String sector;
    private String industry;
    private String basicIndustry;
    private Long totalBuyQuantity;
    private Long totalSellQuantity;
    private Long buyQuantity;
    private Long sellQuantity;
    private StockPrice price;
    private DayMinMax dayMinMax;
    private WeekMinMax weekMinMax;

    private LocalDate toLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }

    private LocalDateTime toLocalDateTime(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
    }

    @JsonProperty("info")
    private void infoProperty(Map<String, Object> infoMap){
        this.symbol = (String) infoMap.get("symbol");
        this.companyName = (String) infoMap.get("companyName");

    }

    @JsonProperty("metadata")
    private void metadataProperty(Map<String, Object> metadatMap){
        this.listingDate = toLocalDate((String)metadatMap.get("listingDate"));

        if(this.price == null){
            this.price = new StockPrice();
        }

        this.price.setTimeOfValue(toLocalDateTime((String)metadatMap.get("lastUpdateTime")));
    }

    @JsonProperty("securityInfo")
    private void securityProperty(Map<String, Object> securityMap){
        this.issuedSize = (Long)securityMap.get("issuedSize");
    }

    @JsonProperty("industryInfo")
    private void industryProperty(Map<String, Object> industryMap){
        this.sector = (String) industryMap.get("sector");
        this.industry = (String) industryMap.get("industry");
        this.basicIndustry = (String) industryMap.get("basicIndustry");
    }

    @JsonProperty("preOpenMarket")
    private void preOpenProperty(Map<String, Object> preOpenMap){
        this.totalBuyQuantity = (Long) preOpenMap.get("totalBuyQuantity");
        this.totalSellQuantity = (Long) preOpenMap.get("totalSellQuantity");
        this.buyQuantity = (Long)preOpenMap.get("atoBuyQty");
        this.sellQuantity = (Long)preOpenMap.get("atoSellQty");
    }

    @SuppressWarnings(value = "unchecked")
    @JsonProperty("priceInfo")
    public void priceInfoProperty(Map<String, Object> priceInfoMap){
        if(this.price == null){
            this.price = new StockPrice();
        }

        if(this.dayMinMax == null){
            this.dayMinMax = new DayMinMax();
        }

        if(this.weekMinMax == null){
            this.weekMinMax = new WeekMinMax();
        }

        this.price.setValue(Double.valueOf(priceInfoMap.get("lastPrice").toString()));
        this.price.setChange(Double.valueOf(priceInfoMap.get("change").toString()));
        this.price.setPercentChange(Double.valueOf(priceInfoMap.get("pChange").toString()));
        this.price.setCloseValue(Double.valueOf(priceInfoMap.get("close").toString()));
        this.price.setOpenValue(Double.valueOf(priceInfoMap.get("open").toString()));
        this.price.setPreviousCloseValue(Double.valueOf(priceInfoMap.get("previousClose").toString()));

        Map<String, Object> dayMap = (Map<String, Object>)priceInfoMap.get("intraDayHighLow");
        this.dayMinMax.setMinValue(Double.valueOf(dayMap.get("min").toString()));
        this.dayMinMax.setMaxValue(Double.valueOf(dayMap.get("max").toString()));

        Map<String, Object> weekMap = (Map<String, Object>)priceInfoMap.get("weekHighLow");
        this.weekMinMax.setMaxValue(Double.valueOf(weekMap.get("max").toString()));
        this.weekMinMax.setMaxValueDate(toLocalDate((String)weekMap.get("maxDate")));
        this.weekMinMax.setMinValue(Double.valueOf(weekMap.get("min").toString()));
        this.weekMinMax.setMinValueDate(toLocalDate((String)weekMap.get("minDate")));

    }
}

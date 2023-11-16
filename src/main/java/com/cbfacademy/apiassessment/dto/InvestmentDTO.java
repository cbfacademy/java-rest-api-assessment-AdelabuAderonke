package com.cbfacademy.apiassessment.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvestmentDTO {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String symbol;
    @NonNull
    private String issuer;
    //private Date purchaseDate;
    @NonNull
    private double purchasePrice;
    @NonNull
    private int quantity;
    @NonNull
    private double investmentAmount;
    @NonNull
    private double currentPrice;
    @NonNull
    private double  currentMarketValue;

}

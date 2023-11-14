package com.cbfacademy.apiassessment.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
@Getter
@Setter
public class InvestmentDTO {
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
    //private double currentPrice;
    //private double  currentMarketValue;

}

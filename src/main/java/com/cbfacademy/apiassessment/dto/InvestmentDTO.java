package com.cbfacademy.apiassessment.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestmentDTO {
    private long id;

    private String name;

    private String symbol;

    private String issuer;
    //private Date purchaseDate;

    private double purchasePrice;

    private int quantity;

    private double investmentAmount;

    private double currentPrice;

    private double  currentMarketValue;

    public InvestmentDTO(String name, String symbol, String issuer, double purchasePrice, int quantity, double investmentAmount, double currentPrice, double currentMarketValue) {
        this.name = name;
        this.symbol = symbol;
        this.issuer = issuer;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.investmentAmount = investmentAmount;
        this.currentPrice = currentPrice;
        this.currentMarketValue = currentMarketValue;
    }
}

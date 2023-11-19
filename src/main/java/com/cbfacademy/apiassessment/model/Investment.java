package com.cbfacademy.apiassessment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Investment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "symbol", unique = true, nullable = false)
    private String symbol;
    @Column(name = "issuer", unique = true, nullable = false)
    private String issuer;
    //private Date purchaseDate;
    @Column(name = "purchasePrice", unique = true, nullable = false)
    private double purchasePrice;
    @Column(name = "quantity", unique = true, nullable = false)
    private int quantity;
    @Column(name = "investmentAmount",nullable = false)
    private double investmentAmount;
    @Column(name = "currentPrice",nullable = false)
    private double currentPrice;
    @Column(name = "currentMarketValue",nullable = false)
    private double currentMarketValue;

    @PrePersist
    @PreUpdate
    private void calculateMarketValue() {
        this.currentMarketValue = this.quantity * this.currentPrice;
    }


    public Investment(Portfolio portfolio, String name, String symbol, String issuer, //Date purchaseDate,
                      double purchasePrice, int quantity, double investmentAmount, double currentPrice, double currentMarketValue) {
        this.portfolio = portfolio;
        this.name = name;
        this.symbol = symbol;
        this.issuer = issuer;
        //this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.investmentAmount = investmentAmount;
        this.currentPrice = currentPrice;
        calculateMarketValue();
    }
}

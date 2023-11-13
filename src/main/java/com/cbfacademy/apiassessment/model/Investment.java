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
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    private String name;
    private String symbol;
    private String issuer;
    private Date purchaseDate;
    private double purchasePrice;
    private int quantity;
    private double currency;
    private double currentPrice;
    private double currentMarketValue;

    // Constructors, getters, setters, and other methods...
    public Investment(Portfolio portfolio, String name, String symbol, String issuer, Date purchaseDate,
                      double purchasePrice, int quantity, double currency, double currentPrice, double currentMarketValue) {
        this.portfolio = portfolio;
        this.name = name;
        this.symbol = symbol;
        this.issuer = issuer;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.currency = currency;
        this.currentPrice = currentPrice;
        this.currentMarketValue = currentMarketValue;
    }
}

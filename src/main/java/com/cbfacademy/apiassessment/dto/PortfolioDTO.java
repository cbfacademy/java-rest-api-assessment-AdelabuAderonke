package com.cbfacademy.apiassessment.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PortfolioDTO {
    @NonNull
    private String portfolioName;
    @NonNull
    private long userId;
    @NonNull
    private String description;

    private Set<InvestmentDTO> investments;

}
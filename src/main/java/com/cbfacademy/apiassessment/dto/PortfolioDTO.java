package com.cbfacademy.apiassessment.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PortfolioDTO {
    private long id;
    @NonNull
    private String portfolioName;
    @NonNull
    private long userId;
    @NonNull
    private String description;

    private Set<InvestmentDTO> investments;

}
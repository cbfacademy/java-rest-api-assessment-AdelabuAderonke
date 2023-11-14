package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.dto.PortfolioDTO;

public interface InvestmentService {
    InvestmentDTO createInvestment(long portfolioId, InvestmentDTO investmentDTO);
    void saveToJSON();
}

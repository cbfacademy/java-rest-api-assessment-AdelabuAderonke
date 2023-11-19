package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvestmentService {
    InvestmentDTO createInvestment(long portfolioId, InvestmentDTO investmentDTO);
    List<InvestmentDTO> getInvestmentByPortfolioId(long portfolioId);
    InvestmentDTO getInvestmentById(long portfolioId,long investmentId);
    InvestmentDTO updateInvestment(Long portfolioId,long investmentId, InvestmentDTO updatedInvestment);
    void DeleteInvestment(long portfolioId, long investmentId);
}

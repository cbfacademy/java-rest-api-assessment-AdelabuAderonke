package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.payload.PortfolioDTO;

import java.util.List;

public interface PortfolioService {
    PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO);
    List<PortfolioDTO> getAllPortfolios();
    PortfolioDTO getPortfolioById(long id);
    PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id);
    void deletePortfolio(long id);
    void saveToJSON(List<PortfolioDTO> portfolioDTOS);
}

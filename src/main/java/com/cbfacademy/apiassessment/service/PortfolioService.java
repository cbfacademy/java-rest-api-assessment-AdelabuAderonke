package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.model.Portfolio;

import java.util.List;

public interface PortfolioService {
    PortfolioDTO getPortfolioById(long id);
    List<PortfolioDTO> getAllPortfolios();
    PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO);
    PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id);
    void deletePortfolio(long id);
    void saveToJSON();
}

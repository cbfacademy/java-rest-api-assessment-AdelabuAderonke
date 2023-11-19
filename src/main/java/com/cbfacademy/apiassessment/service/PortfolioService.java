package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.model.Portfolio;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PortfolioService {
    PortfolioDTO getPortfolioById(long id);
    List<PortfolioDTO> getAllPortfolios();
    PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO);
    PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id);
    void deletePortfolio(long id);
    //List<PortfolioDTO> searchPortfolios(String keyword);
    void saveToJSON();
}

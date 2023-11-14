package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.exception.ResourceNotFoundException;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.InvestmentService;
import com.cbfacademy.apiassessment.service.MarketValueService;
import com.cbfacademy.apiassessment.utils.InvestmentFileUtils;
import com.cbfacademy.apiassessment.utils.PortfolioFileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class InvestmentServiceImpl implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final MarketValueService marketValueService;
    private final PortfolioRepository portfolioRepository;
    private List<Investment> investments;
    public  InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                  MarketValueService marketValueService,
                                  PortfolioRepository portfolioRepository,
                                  List<Investment> investments){
        this.investmentRepository = investmentRepository;
        this.marketValueService = marketValueService;
        this.portfolioRepository = portfolioRepository;
        // Load existing investments from JSON file
        this.investments = InvestmentFileUtils.readInvestmentsFromJson();
        if (this.investments == null) {
            // If file doesn't exist or error reading, initialize with an empty list
            this.investments = new ArrayList<>();
        }
    }
    @Override
    public InvestmentDTO createInvestment(long portfolioId, InvestmentDTO investmentDTO) {
        Investment investment = mapToEntity(investmentDTO);
        //get portfolio by id
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));
        //set portfolio to investment entity
        investment.setPortfolio(portfolio);
        // Calculate investmentAmount
        double investmentAmount = investmentDTO.getPurchasePrice() * investmentDTO.getQuantity();
        investmentDTO.setInvestmentAmount(investmentAmount);

        double currentPrice = marketValueService.getCurrentMarketValue(investmentDTO.getSymbol());
        investment.setCurrentPrice(currentPrice);
        investment.setCurrentMarketValue(investmentDTO.getQuantity() * currentPrice);
        //investment entity to DB
        Investment newInvestment = investmentRepository.save(investment);

        //for JSON file
        //investments.add(investment); // Assuming 'portfolios' is the list you're managing
        //saveToJSON(); // Save to JSON
        return mapToDTO(newInvestment);


    }
    // mapToDTO
    private InvestmentDTO mapToDTO(Investment investment) {
        InvestmentDTO investmentDTO = new InvestmentDTO();
        investmentDTO.setName(investment.getName());
        investmentDTO.setIssuer(investment.getIssuer());
        investmentDTO.setInvestmentAmount(investment.getInvestmentAmount());
        investmentDTO.setSymbol(investment.getSymbol());
        investmentDTO.setQuantity(investment.getQuantity());
        investmentDTO.setPurchasePrice(investment.getPurchasePrice());
        return investmentDTO;

    }

    // mapToEntity
    private Investment mapToEntity(InvestmentDTO investmentDTO) {
        Investment investment = new Investment();
        investment.setName(investmentDTO.getName());
        investment.setIssuer(investmentDTO.getIssuer());
        investment.setInvestmentAmount(investmentDTO.getInvestmentAmount());
        investment.setSymbol(investmentDTO.getSymbol());
        investment.setQuantity(investmentDTO.getQuantity());
        investment.setPurchasePrice(investmentDTO.getPurchasePrice());
        return investment;
    }
    @Override
    public void saveToJSON() {
        InvestmentFileUtils.writeInvestmentsToJson(investments);
    }
}

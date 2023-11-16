package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.exception.ResourceNotFoundException;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.InvestmentService;
import com.cbfacademy.apiassessment.service.MarketValueService;
import com.cbfacademy.apiassessment.utils.InvestmentFileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final MarketValueService marketValueService;
    private final PortfolioRepository portfolioRepository;
    private List<InvestmentDTO> investments;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 MarketValueService marketValueService,
                                 PortfolioRepository portfolioRepository) throws IOException {
        this.investmentRepository = investmentRepository;
        this.marketValueService = marketValueService;
        this.portfolioRepository = portfolioRepository;
        List<InvestmentDTO> investmentFromFile = InvestmentFileUtils.readInvestmentsFromJson();

        if (investmentFromFile == null) {
            this.investments = Collections.synchronizedList(new ArrayList<>());
        } else {
            this.investments = Collections.synchronizedList(new ArrayList<>(investmentFromFile));
        }
    }

    @Override
    public InvestmentDTO createInvestment(long portfolioId, InvestmentDTO investmentDTO) {
        Investment investment = mapToEntity(investmentDTO);
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));
        investment.setPortfolio(portfolio);

        double investmentAmount = investmentDTO.getPurchasePrice() * investmentDTO.getQuantity();
        investment.setInvestmentAmount(investmentAmount);

        double currentPrice = marketValueService.getCurrentMarketValue(investmentDTO.getSymbol());
        investment.setCurrentPrice(currentPrice);
        investment.setCurrentMarketValue(investmentDTO.getQuantity() * currentPrice);

        Investment newInvestment = investmentRepository.save(investment);
        InvestmentDTO response = mapToDTO(newInvestment);

        // Synchronize access to the list
        synchronized (investments) {
            if (!investments.contains(response)) {
                investments.add(response);
            }
        }

        InvestmentFileUtils.writeInvestmentsToJson(investments);

        return response;
    }

    private InvestmentDTO mapToDTO(Investment investment) {
        InvestmentDTO investmentDTO = new InvestmentDTO();
        investmentDTO.setId(investment.getId());
        investmentDTO.setName(investment.getName());
        investmentDTO.setIssuer(investment.getIssuer());
        investmentDTO.setInvestmentAmount(investment.getInvestmentAmount());
        investmentDTO.setSymbol(investment.getSymbol());
        investmentDTO.setQuantity(investment.getQuantity());
        investmentDTO.setPurchasePrice(investment.getPurchasePrice());
        investmentDTO.setCurrentPrice(investment.getCurrentPrice());
        investmentDTO.setCurrentMarketValue(investment.getCurrentMarketValue());
        return investmentDTO;
    }

    private Investment mapToEntity(InvestmentDTO investmentDTO) {
        Investment investment = new Investment();
        investment.setName(investmentDTO.getName());
        investment.setIssuer(investmentDTO.getIssuer());
        investment.setInvestmentAmount(investmentDTO.getInvestmentAmount());
        investment.setCurrentPrice(investmentDTO.getCurrentPrice());
        investment.setCurrentMarketValue(investmentDTO.getCurrentMarketValue());
        investment.setSymbol(investmentDTO.getSymbol());
        investment.setQuantity(investmentDTO.getQuantity());
        investment.setPurchasePrice(investmentDTO.getPurchasePrice());
        return investment;
    }
}

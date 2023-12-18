package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.exception.ResourceNotFoundException;
import com.cbfacademy.apiassessment.exception.StockPortfolioAPIException;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.InvestmentRepository;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.InvestmentService;
import com.cbfacademy.apiassessment.service.MarketValueService;
import com.cbfacademy.apiassessment.utils.InvestmentFileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImpl implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final MarketValueService marketValueService;
    private final PortfolioRepository portfolioRepository;
    private ModelMapper mapper;
    private List<InvestmentDTO> investments;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 MarketValueService marketValueService,
                                 ModelMapper mapper,
                                 PortfolioRepository portfolioRepository) throws IOException {
        this.investmentRepository = investmentRepository;
        this.marketValueService = marketValueService;
        this.mapper = mapper;
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
        InvestmentDTO response = mapToDTO(investment);

        // Synchronize access to the list
        synchronized (investments) {
            if (!investments.contains(response)) {
                investments.add(response);
            }
        }

        InvestmentFileUtils.writeInvestmentsToJson(investments);

        return response;
    }

    @Override
    public List<InvestmentDTO> getInvestmentByPortfolioId(long portfolioId) {
        List<Investment> investments = investmentRepository.findByPortfolioId(portfolioId);
        //convert list of investment entities to list of investment DTOs
        return investments.stream().map(investment -> mapToDTO(investment)).collect(Collectors.toList());
    }

    @Override
    public InvestmentDTO getInvestmentById(long portfolioId, long investmentId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));
        //retrieve investment by Id
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(()-> new ResourceNotFoundException("Investment","id",investmentId));
        if(investment.getPortfolio().getId() == portfolio.getId()){
            return mapToDTO(investment);
        }else{
            throw new StockPortfolioAPIException(HttpStatus.BAD_REQUEST,"Investment does not belong to post");
        }
    }

    @Override
    public InvestmentDTO updateInvestment(long portfolioId, long investmentId, InvestmentDTO investmentRequest) {
        try {
            Portfolio portfolio = portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));

            // retrieve investment by Id
            Investment investment = investmentRepository.findById(investmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Investment", "id", investmentId));

            if (investment.getPortfolio().getId() != portfolio.getId()) {
                throw new StockPortfolioAPIException(HttpStatus.BAD_REQUEST, "Investment does not belong to portfolio");
            }

            investment.setName(investmentRequest.getName());
            investment.setIssuer(investmentRequest.getIssuer());
            investment.setSymbol(investmentRequest.getSymbol());
            investment.setQuantity(investmentRequest.getQuantity());
            investment.setPurchasePrice(investmentRequest.getPurchasePrice());

            double investmentAmount = investmentRequest.getPurchasePrice() * investmentRequest.getQuantity();
            investment.setInvestmentAmount(investmentAmount);

            double currentPrice = marketValueService.getCurrentMarketValue(investmentRequest.getSymbol());
            investment.setCurrentPrice(currentPrice);

            investment.setCurrentMarketValue(investmentRequest.getQuantity() * currentPrice);

            Investment  newUpdatedInvestment = investmentRepository.save(investment);
            return mapToDTO(newUpdatedInvestment);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace(); // replace with appropriate logging mechanism

            // Rethrow the exception or handle it based on your application's needs
            throw new StockPortfolioAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating investment");
        }
    }


    @Override
    public void deleteInvestment(long portfolioId, long investmentId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));
        //retrieve investment by Id
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(()-> new ResourceNotFoundException("Investment","id",investmentId));
        if(investment.getPortfolio().getId() != portfolio.getId()){
            throw new StockPortfolioAPIException(HttpStatus.BAD_REQUEST,"Investment does not belong to portfolio");
        }
        investmentRepository.delete(investment);
    }
    public List<InvestmentDTO> searchInvestments(String query) {
        List<Investment> investmentList = investmentRepository.searchInvestments(query);
        if(investmentList == null){
            return null;
        }
        return investmentList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InvestmentDTO mapToDTO(Investment investment) {
        InvestmentDTO investmentDTO = mapper.map(investment, InvestmentDTO.class);
        return investmentDTO;
    }

    private Investment mapToEntity(InvestmentDTO investmentDTO) {
        Investment investment = mapper.map(investmentDTO,Investment.class);
        return investment;
    }
}

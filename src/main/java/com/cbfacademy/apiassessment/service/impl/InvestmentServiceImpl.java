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
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", portfolioId));
        //retrieve investment by Id
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(()-> new ResourceNotFoundException("Investment","id",investmentId));
        if(investment.getPortfolio().getId() != portfolio.getId()){
            throw new StockPortfolioAPIException(HttpStatus.BAD_REQUEST,"Investment does not belong to portfolio");
        }
        investment.setName(investmentRequest.getName());
        investment.setIssuer(investmentRequest.getIssuer());
        investment.setSymbol(investmentRequest.getSymbol());
        investment.setQuantity(investmentRequest.getQuantity());
        investment.setPurchasePrice(investmentRequest.getPurchasePrice());
//        double investmentAmount = investmentRequest.getPurchasePrice() * investmentRequest.getQuantity();
//        investment.setInvestmentAmount(investmentAmount);
//
//        double currentPrice = marketValueService.getCurrentMarketValue(investmentRequest.getSymbol());
//        if(currentPrice == 0){
//            investment.setCurrentPrice(0);
//        }
//        investment.setCurrentPrice(currentPrice);
//        investment.setCurrentMarketValue(investmentRequest.getQuantity() * currentPrice);

        // Update JSON file
//        for (InvestmentDTO investment1 : investments) {
//            if (investment1.getId() == investmentId) {
//                investment1.setName(investmentRequest.getName());
//                investment1.setIssuer(investmentRequest.getIssuer());
//                break;
//            }
//        }

        Investment newUpdatedinvestment = investmentRepository.save(investment);
        return mapToDTO(newUpdatedinvestment);


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
        return investmentList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InvestmentDTO mapToDTO(Investment investment) {
        InvestmentDTO investmentDTO = mapper.map(investment, InvestmentDTO.class);
//        investmentDTO.setId(investment.getId());
//        investmentDTO.setName(investment.getName());
//        investmentDTO.setIssuer(investment.getIssuer());
//        investmentDTO.setInvestmentAmount(investment.getInvestmentAmount());
//        investmentDTO.setSymbol(investment.getSymbol());
//        investmentDTO.setQuantity(investment.getQuantity());
//        investmentDTO.setPurchasePrice(investment.getPurchasePrice());
//        investmentDTO.setCurrentPrice(investment.getCurrentPrice());
//        investmentDTO.setCurrentMarketValue(investment.getCurrentMarketValue());
        return investmentDTO;
    }

    private Investment mapToEntity(InvestmentDTO investmentDTO) {
        Investment investment = mapper.map(investmentDTO,Investment.class);
//        investment.setName(investmentDTO.getName());
//        investment.setIssuer(investmentDTO.getIssuer());
//        investment.setInvestmentAmount(investmentDTO.getInvestmentAmount());
//        investment.setCurrentPrice(investmentDTO.getCurrentPrice());
//        investment.setCurrentMarketValue(investmentDTO.getCurrentMarketValue());
//        investment.setSymbol(investmentDTO.getSymbol());
//        investment.setQuantity(investmentDTO.getQuantity());
//        investment.setPurchasePrice(investmentDTO.getPurchasePrice());
        return investment;
    }
}

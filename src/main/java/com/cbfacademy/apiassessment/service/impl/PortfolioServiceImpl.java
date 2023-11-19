package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.entity.Portfolio;
import com.cbfacademy.apiassessment.exception.ResourceNotFoundException;
import com.cbfacademy.apiassessment.payload.PortfolioDTO;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.PortfolioService;

import java.util.List;
import java.util.stream.Collectors;

public class PortfolioServiceImpl implements PortfolioService {
    private PortfolioRepository portfolioRepository;
    //mapToDTO
    private PortfolioDTO mapToDTO(Portfolio portfolio){
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setId(portfolio.getId());
        portfolioDTO.setName(portfolio.getName());
        portfolioDTO.setUserId(portfolio.getUserId());
        portfolioDTO.setDescription(portfolio.getDescription());
        return portfolioDTO;
    }
    //mapToEntity
    private Portfolio mapToEntity(PortfolioDTO portfolioDTO){
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolio.getName());
        portfolio.setDescription(portfolio.getDescription());
        portfolio.setUserId(portfolio.getUserId());
        return portfolio;

    }

    @Override
    public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
        //convert DTO to entity
        Portfolio portfolio = mapToEntity(portfolioDTO);
        Portfolio newPortfolio = portfolioRepository.save(portfolio);

        //convert entity to DTO
        PortfolioDTO portfolioResponse = mapToDTO(newPortfolio);

        return portfolioResponse;

    }

    @Override
    public List<PortfolioDTO> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        return  portfolios.stream().map(portfolio -> mapToDTO(portfolio)).collect(Collectors.toList());
    }

    @Override
    public PortfolioDTO getPortfolioById(long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Portfolio","id", id));
        return mapToDTO(portfolio);
    }

    @Override
    public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Portfolio","id", id));
        portfolio.setName(portfolioDTO.getName());
        portfolio.setDescription(portfolioDTO.getDescription());
        Portfolio updatePortfolio = portfolioRepository.save(portfolio);
        return  mapToDTO(updatePortfolio);
    }

    @Override
    public void deletePortfolio(long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Portfolio","id", id));
        portfolioRepository.delete(portfolio);
    }

    @Override
    public void saveToJSON(List<PortfolioDTO> portfolioDTOS) {

    }
}

package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.exception.PortfolioAlreadyExistsException;
import com.cbfacademy.apiassessment.exception.ResourceNotFoundException;
import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.PortfolioService;
import com.cbfacademy.apiassessment.utils.PortfolioFileUtils;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cbfacademy.apiassessment.utils.PortfolioFileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private ModelMapper mapper;
    private List<Portfolio> portfolios;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository,
                                ModelMapper mapper,
                                List<Portfolio> portfolios) {
        this.portfolioRepository = portfolioRepository;
        this.mapper = mapper;
        // Load existing portfolios from JSON file
        this.portfolios = PortfolioFileUtils.readPortfoliosFromJson();
        if (this.portfolios == null) {
            // If file doesn't exist or error reading, initialize with an empty list
            this.portfolios = new ArrayList<>();
        }
    }

    // mapToDTO
    private PortfolioDTO mapToDTO(Portfolio portfolio) {
        PortfolioDTO portfolioDTO = mapper.map(portfolio,PortfolioDTO.class);
//        Set<InvestmentDTO> investmentDTOSet = portfolio.getInvestments()
//                .stream()
//                .map(investment -> mapToDTO(investment))
//                .collect(Collectors.toSet());
//        portfolioDTO.setInvestmentDTOSet(investmentDTOSet);
//        //portfolioDTO.setId(portfolio.getId());
//        portfolioDTO.setPortfolioName(portfolio.getPortfolioName());
//        portfolioDTO.setUserId(portfolio.getUserId());
//        portfolioDTO.setDescription(portfolio.getDescription());
        return portfolioDTO;
    }

    private InvestmentDTO mapToDTO(Investment investment) {
        InvestmentDTO investmentDTO = mapper.map(investment, InvestmentDTO.class);
        return  investmentDTO;
    }
    // mapToEntity
    private Portfolio mapToEntity(PortfolioDTO portfolioDTO) {
        Portfolio portfolio = mapper.map(portfolioDTO,Portfolio.class);
       return portfolio;

    }

    @Override
    public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
        if (portfolioRepository.existsById(portfolioDTO.getId())){
            throw new PortfolioAlreadyExistsException("Portfolio with the same name already exists");
            // Create a custom exception class 'PortfolioAlreadyExistsException' or use an existing one
        }
        // convert DTO to entity
        Portfolio portfolio = mapToEntity(portfolioDTO);

        Portfolio newPortfolio = portfolioRepository.save(portfolio);

        //for JSON file
        portfolios.add(portfolio); // Assuming 'portfolios' is the list you're managing
        saveToJSON(); // Save to JSON
        PortfolioDTO portfolioResponse = mapToDTO(newPortfolio);

        return portfolioResponse;

    }

    @Override
    public List<PortfolioDTO> getAllPortfolios() {
        List<Portfolio> portfoliosDB = portfolioRepository.findAll();
        List<Portfolio> mergedPortfolios = new ArrayList<>(portfoliosDB);
        mergedPortfolios.addAll(portfolios);

        return mergedPortfolios.stream().map(this::mapToDTO).collect(Collectors.toList());

        // return portfolios.stream().map(portfolio ->
        // mapToDTO(portfolio)).collect(Collectors.toList());
    }

    @Override
    public PortfolioDTO getPortfolioById(long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", id));
        return mapToDTO(portfolio);
    }

    @Override
    public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", id));
        portfolio.setPortfolioName(portfolioDTO.getPortfolioName());
        portfolio.setDescription(portfolioDTO.getDescription());
        Portfolio updatePortfolio = portfolioRepository.save(portfolio);

        // Update JSON file
        for (Portfolio p : portfolios) {
            if (p.getId() == id) {
                p.setPortfolioName(portfolioDTO.getPortfolioName());
                p.setDescription(portfolioDTO.getDescription());
                break;
            }
        }
        saveToJSON(); // Save to JSON
        return mapToDTO(updatePortfolio);
    }

    @Override
    public void deletePortfolio(long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio", "id", id));
        portfolioRepository.delete(portfolio);

        // Delete from JSON file
        portfolios.removeIf(p -> p.getId() == id);
        // Save to JSON
        saveToJSON();

    }

    @Override
    public void saveToJSON() {
        PortfolioFileUtils.writePortfolioToJson(portfolios);
    }
}

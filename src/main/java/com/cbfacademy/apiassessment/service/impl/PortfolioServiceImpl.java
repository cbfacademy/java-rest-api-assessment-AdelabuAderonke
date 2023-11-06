package com.cbfacademy.apiassessment.service.impl;

import com.cbfacademy.apiassessment.entity.Portfolio;
import com.cbfacademy.apiassessment.payload.PortfolioDTO;
import com.cbfacademy.apiassessment.service.PortfolioService;

import java.util.List;

public class PortfolioServiceImpl implements PortfolioService {

    //mapToDTO
    private PortfolioDTO mapToDTO(Portfolio portfolio){
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setId(portfolio.getId());
        portfolioDTO.setName(portfolio.getName());
        portfolioDTO.
        return portfolioDTO;
    }
    //mapToEntity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;

    }

    @Override
    public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
        return null;
    }

    @Override
    public List<PortfolioDTO> getAllPortfolios() {
        return null;
    }

    @Override
    public PortfolioDTO getPortfolioById(long id) {
        return null;
    }

    @Override
    public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO, long id) {
        return null;
    }

    @Override
    public void deletePortfolio(long id) {

    }

    @Override
    public void saveToJSON(List<PortfolioDTO> portfolioDTOS) {

    }
}

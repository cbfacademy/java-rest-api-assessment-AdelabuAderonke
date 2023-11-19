package com.cbfacademy.apiassessment.controller;

import java.util.List;

import com.cbfacademy.apiassessment.model.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
    //create portfolio post
    @PostMapping
    public ResponseEntity<PortfolioDTO> createPost(@RequestBody PortfolioDTO portfolioDTO){

       return new ResponseEntity<>(portfolioService.createPortfolio(portfolioDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public List<PortfolioDTO> getAllPortfolios (){
        return  portfolioService.getAllPortfolios();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO>getPortfolioById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }
    @PutMapping("/{id}")
    public  ResponseEntity<PortfolioDTO> updatePortfolio(@RequestBody PortfolioDTO portfolioDTO, @PathVariable(name="id") long id){
        PortfolioDTO portfolioResponse = portfolioService.updatePortfolio(portfolioDTO, id);
        return new ResponseEntity<>(portfolioResponse, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable(name="id") long id){
        portfolioService.deletePortfolio(id);
        return new ResponseEntity<>("Portfolio deleted successfully", HttpStatus.OK);
    }

}

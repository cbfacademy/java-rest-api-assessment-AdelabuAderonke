package com.cbfacademy.apiassessment.controller;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvestmentController {
    @Autowired

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping("/portfolio/{portfolioId}/investments")
    public ResponseEntity<InvestmentDTO> createInvestment(@PathVariable(value = "portfolioId") long portfolioId,
                                                          @RequestBody InvestmentDTO investmentDTO){
        return new ResponseEntity<>(investmentService.createInvestment(portfolioId, investmentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/portfolio/{portfolioId}/investments")
    public List<InvestmentDTO> getInvestmentByPortfolioId(@PathVariable(value="portfolioId") long portfolioId) {
        return investmentService.getInvestmentByPortfolioId(portfolioId);
    }
    @GetMapping("/portfolio/{portfolioId}/investments/{investmentId}")
    public ResponseEntity<InvestmentDTO> getInvestmentById(@PathVariable(value="portfolioId") long portfolioId,
                                                           @PathVariable(value="investmentId") long investmentId){
        InvestmentDTO investmentDTO = investmentService.getInvestmentById(portfolioId,investmentId);
        return new ResponseEntity<>(investmentDTO,HttpStatus.OK);
    }
    @PutMapping("/portfolio/{portfolioId}/investments/{investmentId}")
    public ResponseEntity<InvestmentDTO> updateInvestment(@PathVariable(value="portfolioId") long portfolioId,
                                                          @PathVariable(value="investmentId") long investmentId,
                                                          InvestmentDTO updateInvestment){
        InvestmentDTO updatedInvestment = investmentService.updateInvestment(portfolioId, investmentId, updateInvestment);
        return new ResponseEntity<>(updatedInvestment, HttpStatus.OK);
    }
}

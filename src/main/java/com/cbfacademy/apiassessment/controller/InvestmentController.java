package com.cbfacademy.apiassessment.controller;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InvestmentController {

    private final InvestmentService investmentService;
    @Autowired

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping("/portfolio/{portfolioId}/investments")
    public ResponseEntity<InvestmentDTO> createInvestment(@PathVariable(value = "portfolioId") long portfolioId,
                                                          @RequestBody InvestmentDTO investmentDTO){
        return new ResponseEntity<>(investmentService.createInvestment(portfolioId, investmentDTO), HttpStatus.CREATED);
    }

}

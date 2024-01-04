package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.repository.PortfolioRepository;
import com.cbfacademy.apiassessment.service.InvestmentService;
import com.cbfacademy.apiassessment.service.PortfolioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest
public class InvestmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestmentService investmentService;
    @MockBean
    private PortfolioRepository portfolioRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void givenInvestmentObject_whenCreateInvestment_thenReturnSavedInvestments() throws Exception{
        Investment investment = Investment.builder()
                .name("Google")
                .symbol("GOOGL")
                .issuer("Alphabet Inc.")
                .purchasePrice(2000.0)
                .quantity(8)
                .investmentAmount(16000.0)
                .currentPrice(210.5)
                .currentMarketValue(1684.0)
                .build();
        // Perform the request and check the response
        mockMvc.perform(post("/api/portfolio/253/investments")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(investment)))
                .andExpect(status().isCreated());
    }


}

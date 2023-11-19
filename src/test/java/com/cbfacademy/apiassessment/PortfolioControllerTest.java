package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.dto.PortfolioDTO;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.service.PortfolioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PortfolioService portfolioService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenPortfolioObject_whenCreatePortfolio_thenReturnSavedPortfolio() throws Exception {
        // given - precondition or setup
        Set<Investment> investments = new HashSet<>();
        investments.add(Investment.builder()
                .name("Amazon.com")
                .symbol("AMZN")
                .issuer("Amazon.com Inc.")
                .purchasePrice(3000.0)
                .quantity(5)
                .investmentAmount(15000.0)
                .currentPrice(145.18)
                .currentMarketValue(725.9)
                .build());
        Portfolio portfolio = Portfolio.builder()
                .portfolioName("Tech Portfolio")
                .description("Tech related portfolio")
                .userId(1L)
                .investments(investments)
                .build();

        // Perform the request and check the response
        mockMvc.perform(post("/api/portfolios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(portfolio)))
                .andExpect(status().isCreated());

    }
@Test
    // JUnit test for Get All portfolios REST API
    public void givenListOfPortfolios_whenGetAllPortfolios_thenReturnPortfoliosList() throws Exception {
        // given - precondition or setup
        List<PortfolioDTO> listOfPortfolios = new ArrayList<>();

        PortfolioDTO portfolio1 = PortfolioDTO.builder()
                .portfolioName("Real Estate")
                .description("Mostly Houses")
                .userId(2L)
                .build();

        Set<InvestmentDTO> investments1 = new HashSet<>();
        investments1.add(InvestmentDTO.builder()
                .name("Amazon.com")
                .symbol("AMZN")
                .issuer("Amazon.com Inc.")
                .purchasePrice(3000.0)
                .quantity(5)
                .investmentAmount(15000.0)
                .currentPrice(145.18)
                .currentMarketValue(725.9)
                .build());

        Set<InvestmentDTO> investments2 = new HashSet<>();
        investments2.add(InvestmentDTO.builder()
                .name("Google")
                .symbol("GOOGL")
                .issuer("Alphabet Inc.")
                .purchasePrice(2000.0)
                .quantity(8)
                .investmentAmount(16000.0)
                .currentPrice(210.5)
                .currentMarketValue(1684.0)
                .build());

        portfolio1.setInvestments(investments1);

        PortfolioDTO portfolio2 = PortfolioDTO.builder()
                .portfolioName("Tech Stocks")
                .description("Investing in Tech Companies")
                .userId(3L)
                .build();
        portfolio2.setInvestments(investments2);

        listOfPortfolios.add(portfolio1);
        listOfPortfolios.add(portfolio2);

        // Mocking the service call
        when(portfolioService.getAllPortfolios()).thenReturn(listOfPortfolios);

        // Performing the GET request
        mockMvc.perform(get("/api/portfolios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].portfolioName", is("Real Estate")))
                .andExpect(jsonPath("$[0].investments[0].name", is("Amazon.com")))
                .andExpect(jsonPath("$[1].portfolioName", is("Tech Stocks")))
                .andExpect(jsonPath("$[1].investments[0].name", is("Google")));
    }
            
}
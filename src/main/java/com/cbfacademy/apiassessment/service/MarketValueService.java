package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.MarketValueResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class MarketValueService {
    @Value("${financial.api.key}")
    private String apiKey;  // External API key

    private final RestTemplate restTemplate;

    public MarketValueService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getCurrentMarketValue(String symbol) {
        String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey={apiKey}";
        return restTemplate.getForObject(apiUrl, MarketValueResponse.class, symbol, apiKey)
                .getGlobalQuote()
                .getCurrentPrice();
    }
}

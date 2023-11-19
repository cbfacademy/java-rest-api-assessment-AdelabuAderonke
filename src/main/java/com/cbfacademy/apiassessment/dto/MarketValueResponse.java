package com.cbfacademy.apiassessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketValueResponse {

    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public static class GlobalQuote {

        @JsonProperty("05. price")
        private Double currentPrice;  // Use Double instead of double to allow null

        public Double getCurrentPrice() {
            return currentPrice;
        }
    }
}

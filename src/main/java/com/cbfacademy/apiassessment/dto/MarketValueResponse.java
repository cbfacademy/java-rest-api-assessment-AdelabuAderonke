package com.cbfacademy.apiassessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
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
        private double currentPrice;

        public double getCurrentPrice() {
            return currentPrice;
        }
    }
}

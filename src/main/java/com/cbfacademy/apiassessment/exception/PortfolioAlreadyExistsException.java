package com.cbfacademy.apiassessment.exception;

public class PortfolioAlreadyExistsException extends RuntimeException{
    public PortfolioAlreadyExistsException(String message) {
        super(message);
    }

    public PortfolioAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.cbfacademy.apiassessment.exception;

public class InvestmentFileException extends RuntimeException{
    public InvestmentFileException(String message) {
        super(message);
    }

    public InvestmentFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

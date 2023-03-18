package com.trade.currency.cutoffCalculator.exception;

public class InvalidCurrency extends RuntimeException{

    public InvalidCurrency(String currency ){
        super("Input currency parameter : "+ currency+" is invalid");
    }
}

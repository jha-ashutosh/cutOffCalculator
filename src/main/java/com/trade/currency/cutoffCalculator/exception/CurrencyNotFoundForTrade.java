package com.trade.currency.cutoffCalculator.exception;



public class CurrencyNotFoundForTrade  extends RuntimeException{
    public CurrencyNotFoundForTrade(String currency){
        super("Input currency :"+currency+" not found for Trade List");

    }
}

package com.trade.currency.cutoffCalculator.exception;

import org.springframework.web.bind.annotation.ResponseBody;


public class InvalidInputDate extends RuntimeException{
    public InvalidInputDate(String date) {
        super(" Input date(YYYY-MM-DD) is either invalid or past date : "+date);
    }
}

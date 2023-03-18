package com.trade.currency.cutoffCalculator.dao;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String status="Failed";
    private String errorMessage;

}

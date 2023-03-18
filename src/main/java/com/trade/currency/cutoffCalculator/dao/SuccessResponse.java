package com.trade.currency.cutoffCalculator.dao;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SuccessResponse {

    private String date;
    private String cutoffTime;
}

package com.trade.currency.cutoffCalculator.Model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="tbl_currency_cutoff")
public class CurrencyCutoff {

    @Id
    @Column(name="ISO")
    private String iso;
    @Column(name="Country")
    private String country;
    @Column(name="Today")
    private String today;
    @Column(name="Tomorrow")
    private String tomorrow;
    @Column(name="After_Tomorrow")
    private String afterTomorrow;
}

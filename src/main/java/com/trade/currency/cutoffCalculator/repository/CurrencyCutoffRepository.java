package com.trade.currency.cutoffCalculator.repository;

import com.trade.currency.cutoffCalculator.Model.CurrencyCutoff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyCutoffRepository extends JpaRepository<CurrencyCutoff,String> {
}

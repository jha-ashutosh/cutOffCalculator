package com.trade.currency.cutoffCalculator.controller;

import com.trade.currency.cutoffCalculator.Model.CurrencyCutoff;
import com.trade.currency.cutoffCalculator.dao.SuccessResponse;
import com.trade.currency.cutoffCalculator.exception.CurrencyNotFoundForTrade;
import com.trade.currency.cutoffCalculator.repository.CurrencyCutoffRepository;
import com.trade.currency.cutoffCalculator.service.CurrencyCutoffService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
@Validated
@RequestMapping("/api")
@EnableSwagger2
public class CutOffController {

    @Autowired
    private CurrencyCutoffRepository repository;

    @Autowired
    private CurrencyCutoffService service;

   /*
    @PostMapping("/saveCurrencyCutoff")
    public String saveCurrencyCutoff(@RequestBody CurrencyCutoff currencyCutoff){
        repository.save(currencyCutoff);
        return "currencyCutoff saved";

    }
    @GetMapping("/getCurrencyCutoffs")
    public List<CurrencyCutoff> getAll(){
        return repository.findAll();
    }
    @GetMapping("/getCurrencyCutoffs/{currency}")
    public CurrencyCutoff getCurrencyCutOff(@PathVariable String currency){
        return repository.findById(currency).orElseThrow(() -> new CurrencyNotFoundForTrade(currency));
    }

    */
@ApiOperation(value="get Currency Trade Cutoff Service ")

    @GetMapping("/getCurrencyTradeCutoff/{currency1}/{currency2}/{date}")
    public ResponseEntity<SuccessResponse> getCurrencyTradeCutoff(@PathVariable   String currency1,
                                                                  @PathVariable   String currency2,
                                                                  @PathVariable  String date){


        service.validateCutoffDateParameter(currency1,currency2,date);
        CurrencyCutoff currency1Details, currency2Details;
         currency1Details= repository.findById(currency1).orElseThrow(() -> new CurrencyNotFoundForTrade(currency1));
         currency2Details=repository.findById(currency2).orElseThrow(() -> new CurrencyNotFoundForTrade(currency2));
         String cutoffDate=service.cutOffDateCalculator(date);

        return new ResponseEntity<>(service.cutOffTime(currency1Details,currency2Details,cutoffDate,date),HttpStatus.ACCEPTED);

    }

}

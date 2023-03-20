package com.trade.currency.cutoffCalculator.service;

import com.trade.currency.cutoffCalculator.Model.CurrencyCutoff;
import com.trade.currency.cutoffCalculator.dao.SuccessResponse;
import com.trade.currency.cutoffCalculator.exception.InvalidCurrency;
import com.trade.currency.cutoffCalculator.exception.InvalidInputDate;
import com.trade.currency.cutoffCalculator.repository.CurrencyCutoffRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@EnableSwagger2
public class CurrencyCutoffService {
    @Autowired
    private CurrencyCutoffRepository repository;

//Method to check string value is number or not
    public  boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

//Method to calculate cutoff time and return response
    public SuccessResponse cutOffTime(CurrencyCutoff cur1Detail, CurrencyCutoff cur2Detail, String cutOffDate, String inputDate){

        String result="";
        String earliestCutoffCur1="";
        String earliestCutoffCur2="";
        switch (cutOffDate) {
            case "today":
                earliestCutoffCur1=cur1Detail.getToday();
                earliestCutoffCur2=cur2Detail.getToday();
                break;
            case "tomorrow":
                earliestCutoffCur1=cur1Detail.getTomorrow();
                earliestCutoffCur2=cur2Detail.getTomorrow();
                break;
            case "afterTomorrow":
                earliestCutoffCur1=cur1Detail.getAfterTomorrow();
                earliestCutoffCur2=cur2Detail.getAfterTomorrow();
                break;
            default:

        }



        if(earliestCutoffCur1.equalsIgnoreCase("Always possible") && earliestCutoffCur2.equalsIgnoreCase("Always possible")){
            result="Always possible";
        }
        else if(earliestCutoffCur1.equalsIgnoreCase("Never possible") || earliestCutoffCur2.equalsIgnoreCase("Never possible")){
            result= "Never possible";
        }
        else if(earliestCutoffCur1.equalsIgnoreCase("Never possible") && earliestCutoffCur2.equalsIgnoreCase("Never possible")){
            result= "Never possible";
        }else if(isNumeric(earliestCutoffCur1) && earliestCutoffCur2.equalsIgnoreCase("Always possible")){
            result=earliestCutoffCur1;
        }

        else if(isNumeric(earliestCutoffCur2) && earliestCutoffCur1.equalsIgnoreCase("Always possible")){
            result=earliestCutoffCur2;
        }else if (isNumeric(earliestCutoffCur2) && isNumeric(earliestCutoffCur1)){
            result =  (Double.parseDouble(earliestCutoffCur1) > Double.parseDouble(earliestCutoffCur2)) ? earliestCutoffCur2 : earliestCutoffCur1;

        }
       // System.out.println(isNumeric(earliestCutoffCur2));
       // System.out.println(isNumeric(earliestCutoffCur1));
      //  System.out.println("result is :"+result);
        SuccessResponse response=new SuccessResponse();
        response.setDate(inputDate);
        response.setCutoffTime(result);
        return response;
    }

//Method to evaluate whether input date is today , tomorrow or After tomorrow 
    public String cutOffDateCalculator(String date) {
        if(LocalDate.parse(date).equals(LocalDate.now())) return "today";
        else if (LocalDate.parse(date).equals(LocalDate.now().plusDays(1))) return "tomorrow";
        else return "afterTomorrow";

    }
// Method to validate input parametes format as valid date and valid format of currency
    public void validateCutoffDateParameter(String currency1, String currency2, String date) {
        if(!Pattern.matches("^[A-Z]{3}$",currency1))  throw new InvalidCurrency(currency1);
        if(!Pattern.matches("^[A-Z]{3}$",currency2))  throw new InvalidCurrency(currency2);
        if(!GenericValidator.isDate(date, "yyyy-MM-dd", true)) throw  new InvalidInputDate(date);
        if(LocalDate.now().isAfter(LocalDate.parse(date))) throw  new InvalidInputDate(date);


    }
}

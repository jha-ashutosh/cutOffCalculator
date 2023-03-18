package com.trade.currency.cutoffCalculator.exception;

import com.trade.currency.cutoffCalculator.dao.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CurrencyNotFoundForTrade.class)
    public ResponseEntity<ErrorResponse> springHandleNotFound(CurrencyNotFoundForTrade response)  {
         return new ResponseEntity<>(getErrorResponseBody(response.getMessage()), HttpStatus.NOT_FOUND);
    }

    private ErrorResponse getErrorResponseBody(String message){
        ErrorResponse responseObj=new ErrorResponse();
        responseObj.setErrorMessage(message);
        return responseObj;

    }
    @ExceptionHandler(InvalidInputDate.class)
    public ResponseEntity<ErrorResponse> handleDateFormat(InvalidInputDate response)  {
        return new ResponseEntity<>(getErrorResponseBody(response.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCurrency.class)
    public ResponseEntity<ErrorResponse> handleInvalidCurrencyFormat(InvalidCurrency response) {
        System.out.println("inside handleEmptyInput ");
        return new ResponseEntity<>(getErrorResponseBody(response.getMessage()), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(value = MethodNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedExceptionException(MethodNotAllowedException ex) {
        return new ResponseEntity<>(getErrorResponseBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("status",status.value());
        System.out.println("inside handleMethodArgumentNotValid ");
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors",errors);
        return new ResponseEntity<>(body, status);
    }






}

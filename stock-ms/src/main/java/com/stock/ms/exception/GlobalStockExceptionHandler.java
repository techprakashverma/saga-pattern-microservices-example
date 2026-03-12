package com.stock.ms.exception;


import com.stock.ms.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalStockExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalStockExceptionHandler.class);
    @Autowired
    private StockService stockService;

    @ExceptionHandler(StockNotFoundException.class)
    public void handleStockNotFoundException(StockNotFoundException ex) {
        logger.info("StockNotFoundException:: {}", ex.getOrder().getItem());
        stockService.sendPaymentReversed(ex.getOrder());
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception ex) {
        ex.printStackTrace();
    }


}

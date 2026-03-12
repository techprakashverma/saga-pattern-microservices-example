package com.stock.ms.exception;

import com.stock.ms.dto.CustomerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StockNotFoundException extends Exception{

    private final CustomerOrder order;

    public StockNotFoundException(String message, CustomerOrder order){
        super(message);
        this.order = order;
    }

    public CustomerOrder getOrder(){
        return order;
    }
}

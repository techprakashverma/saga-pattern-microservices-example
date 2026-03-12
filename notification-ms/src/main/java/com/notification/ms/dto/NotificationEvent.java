package com.notification.ms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {

    private String type;

    private CustomerOrder order;

    public String getType() {
        return type;
    }

    public NotificationEvent setType(String type) {
        this.type = type;
        return this;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public NotificationEvent setOrder(CustomerOrder order) {
        this.order = order;
        return this;
    }
}

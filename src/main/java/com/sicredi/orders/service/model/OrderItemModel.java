package com.sicredi.orders.service.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class OrderItemModel {

    private String id;

    private Double unityPrice;

    private Double quantity;

    private Double total;
}

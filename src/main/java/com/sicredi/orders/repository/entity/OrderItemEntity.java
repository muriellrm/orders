package com.sicredi.orders.repository.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class OrderItemEntity {

    private String id;

    private Double unityPrice;

    private Double quantity;

    private Double total;

}

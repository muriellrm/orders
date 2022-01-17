package com.sicredi.orders.service.model;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemModel {

    private String id;

    private Double unityPrice;

    private Double quantity;

    private Double total;
}

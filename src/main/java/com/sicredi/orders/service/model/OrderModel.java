package com.sicredi.orders.service.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderModel {
    private String orderCode;
    private List<OrderItemModel> items;
    private Double totalPrice;
}

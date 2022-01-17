package com.sicredi.orders.service.model;

import com.sicredi.orders.repository.entity.OrderItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class OrderModel {
    private String orderCode;
    private List<OrderItemModel> items;
    private Double totalPrice;
}

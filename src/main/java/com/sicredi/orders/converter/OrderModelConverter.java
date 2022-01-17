package com.sicredi.orders.converter;

import com.sicredi.orders.repository.entity.OrderEntity;
import com.sicredi.orders.repository.entity.OrderItemEntity;
import com.sicredi.orders.service.model.OrderItemModel;
import com.sicredi.orders.service.model.OrderModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelConverter {

    public OrderModel mapFrom(OrderEntity orderEntity) {
        return OrderModel.builder()
                .orderCode(orderEntity.getOrderCode())
                .items(mapFromItems(orderEntity.getItems()))
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }

    private List<OrderItemModel> mapFromItems(List<OrderItemEntity> items) {
        return items.stream().map(this::mapFromItem).collect(Collectors.toList());
    }

    private OrderItemModel mapFromItem(OrderItemEntity orderItemEntity) {
        return OrderItemModel.builder()
                .id(orderItemEntity.getId())
                .unityPrice(orderItemEntity.getUnityPrice())
                .quantity(orderItemEntity.getQuantity())
                .total(orderItemEntity.getTotal()).build();

    }
}

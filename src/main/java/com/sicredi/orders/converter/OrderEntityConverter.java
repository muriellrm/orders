package com.sicredi.orders.converter;

import com.sicredi.orders.repository.entity.OrderEntity;
import com.sicredi.orders.repository.entity.OrderItemEntity;
import com.sicredi.orders.service.model.OrderItemModel;
import com.sicredi.orders.service.model.OrderModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityConverter {

    public OrderEntity mapFrom(OrderModel orderModel) {
        return applyValues(OrderEntity
                .builder()
                .build(),
                orderModel);
    }

    private List<OrderItemEntity> mapFromItems(List<OrderItemModel> items) {
        return items.stream().map(this::mapFromItem).collect(Collectors.toList());
    }

    private OrderItemEntity mapFromItem(OrderItemModel orderItemModel) {
        return OrderItemEntity.builder()
                .id(orderItemModel.getId())
                .unityPrice(orderItemModel.getUnityPrice())
                .quantity(orderItemModel.getQuantity())
                .total(orderItemModel.getUnityPrice() * orderItemModel.getQuantity()).build();

    }

    public OrderEntity applyValues(OrderEntity orderEntity, OrderModel orderModel) {
        var items = mapFromItems(orderModel.getItems());
        var totalPrice = items.stream().reduce(0.0 ,(acc, value)-> acc + value.getTotal(), Double::sum);

        return orderEntity.toBuilder()
                .orderCode(orderModel.getOrderCode())
                .items(items)
                .totalPrice(totalPrice)
                .build();
    }
}

package com.sicredi.orders.converter;

import com.sicredi.orders.repository.entity.OrderEntity;
import com.sicredi.orders.repository.entity.OrderItemEntity;
import com.sicredi.orders.service.model.OrderItemModel;
import com.sicredi.orders.service.model.OrderModel;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityConverter {

    private final ModelMapper modelMapper;

    public OrderEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    private List<OrderItemEntity> mapFromItems(List<OrderItemModel> items) {
        return items.stream().map(this::mapFromItem).collect(Collectors.toList());
    }

    private OrderItemEntity mapFromItem(OrderItemModel orderItemModel) {
        return modelMapper.map(orderItemModel,OrderItemEntity.class)
                .toBuilder()
                .total(orderItemModel.getUnityPrice() * orderItemModel.getQuantity())
                .build();

    }
    public OrderEntity applyValues(OrderModel orderModel) {
        return applyValues(new OrderEntity(),orderModel);
    }
    public OrderEntity applyValues(OrderEntity orderEntity, OrderModel orderModel) {
        var items = mapFromItems(orderModel.getItems());
        var totalPrice = items.stream().reduce(0.0 ,(acc, value)-> acc + value.getTotal(), Double::sum);
        modelMapper.map(orderModel,orderEntity);
        return orderEntity.toBuilder()
                .items(items)
                .totalPrice(totalPrice)
                .build();
    }
}

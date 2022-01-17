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
       var r = this.modelMapper.typeMap(OrderItemModel.class, OrderItemEntity.class)
                .addMapping(src -> src.getUnityPrice() * src.getQuantity(), (dest,v) -> {
                    dest.setTotal((Double) v);
                })
               .map(orderItemModel);

        return OrderItemEntity.builder()
                .id(orderItemModel.getId())
                .unityPrice(orderItemModel.getUnityPrice())
                .quantity(orderItemModel.getQuantity())
                .total(orderItemModel.getUnityPrice() * orderItemModel.getQuantity()).build();

    }
    public OrderEntity applyValues(OrderModel orderModel) {
        return applyValues(OrderEntity
                        .builder()
                        .build(),
                orderModel);
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

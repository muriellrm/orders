package com.lightspace.orders.converter;

import com.lightspace.orders.repository.entity.OrderEntity;
import com.lightspace.orders.repository.entity.OrderItemEntity;
import com.lightspace.orders.service.model.OrderItemModel;
import com.lightspace.orders.service.model.OrderModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@Component
public class OrderEntityConverter {

    private final ModelMapper modelMapper;

    public OrderEntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private List<OrderItemEntity> mapFromItems(List<OrderItemModel> items) {
        return emptyIfNull(items)
                .stream()
                .map(this::mapFromItem)
                .collect(Collectors.toList());
    }

    private OrderItemEntity mapFromItem(OrderItemModel orderItemModel) {
        return modelMapper.map(orderItemModel,OrderItemEntity.class)
                .toBuilder()
                .id(Optional.ofNullable(orderItemModel.getId()).orElse(UUID.randomUUID().toString()))
                .amount(orderItemModel.getUnityPrice() * orderItemModel.getQuantity())
                .build();

    }
    public OrderEntity applyValues(OrderModel orderModel) {
        return applyValues(new OrderEntity(),orderModel);
    }
    public OrderEntity applyValues(OrderEntity orderEntity, OrderModel orderModel) {
        var items = mapFromItems(orderModel.getItems());
        var amount = items.stream().reduce(0.0 ,(acc, value)-> acc + value.getAmount(), Double::sum);
        modelMapper.map(orderModel,orderEntity);
        return orderEntity.toBuilder()
                .items(items)
                .amount(amount)
                .createdDate(Optional.ofNullable(orderEntity.getCreatedDate()).orElse(new Date(System.currentTimeMillis())))
                .lastModifiedDate(new Date(System.currentTimeMillis()))
                .build();
    }
}

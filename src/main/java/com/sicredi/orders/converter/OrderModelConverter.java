package com.sicredi.orders.converter;
import com.sicredi.orders.repository.entity.OrderEntity;
import com.sicredi.orders.service.model.OrderModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class OrderModelConverter {

    private final ModelMapper modelMapper;

    public OrderModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderModel mapFrom(OrderEntity entity) {
        return modelMapper.map(entity, OrderModel.class);
    }

}

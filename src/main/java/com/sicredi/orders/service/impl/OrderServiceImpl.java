package com.sicredi.orders.service.impl;

import com.sicredi.orders.converter.OrderEntityConverter;
import com.sicredi.orders.converter.OrderModelConverter;
import com.sicredi.orders.repository.OrderRepository;
import com.sicredi.orders.service.OrderService;
import com.sicredi.orders.service.model.OrderFilterModel;
import com.sicredi.orders.service.model.OrderModel;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderModelConverter orderModelConverter;
    private final OrderEntityConverter orderEntityConverter;

    public OrderServiceImpl(OrderRepository orderRepository, OrderModelConverter orderModelConverter, OrderEntityConverter orderEntityConverter) {
        this.orderRepository = orderRepository;
        this.orderModelConverter = orderModelConverter;
        this.orderEntityConverter = orderEntityConverter;
    }

    @Override
    public Mono<OrderModel> findOne(String orderCode) {
        return orderRepository.findOne(orderCode).map(orderModelConverter::mapFrom);
    }

    @Override
    public Mono<OrderModel> save(OrderModel orderModel) {
        return Mono.just(orderModel)
                .map(orderEntityConverter::mapFrom)
                .flatMap(orderRepository::save)
                .map(orderModelConverter::mapFrom);
    }

    @Override
    public Mono<OrderModel> save(String orderCode, OrderModel orderModel) {
        return orderRepository.findOne(orderCode)
                .zipWith(Mono.just(orderModel), orderEntityConverter::applyValues)
                .flatMap(orderRepository::save)
                .map(orderModelConverter::mapFrom);

    }

    @Override
    public Flux<OrderModel> find(OrderFilterModel orderFilterModel) {
        var query = new Query();
        var criteria = new ArrayList<Criteria>();

        if(orderFilterModel.getPriceMin() != null) {
            criteria.add(Criteria.where("totalPrice").gte(orderFilterModel.getPriceMin()));
        }
        if(orderFilterModel.getPriceMax() != null) {
            criteria.add(Criteria.where("totalPrice").lte(orderFilterModel.getPriceMax()));
        }

        if(!CollectionUtils.isEmpty(criteria)) {
            query.addCriteria(new Criteria()
                    .andOperator(
                            criteria.toArray(Criteria[]::new)
                    ));
        }

        return orderRepository.find(query).map(orderModelConverter::mapFrom);
    }

    @Override
    public Mono<Void> remove(String orderCode) {
        return orderRepository.remove(orderCode)
                .then();
    }

}

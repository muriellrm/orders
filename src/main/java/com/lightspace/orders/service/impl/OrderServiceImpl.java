package com.lightspace.orders.service.impl;

import com.lightspace.orders.converter.OrderEntityConverter;
import com.lightspace.orders.converter.OrderModelConverter;
import com.lightspace.orders.repository.OrderRepository;
import com.lightspace.orders.service.OrderService;
import com.lightspace.orders.service.model.OrderFiltersModel;
import com.lightspace.orders.service.model.OrderModel;
import com.lightspace.util.CriteriaUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
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
                .map(orderEntityConverter::applyValues)
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
    public Flux<OrderModel> find(OrderFiltersModel filters) {
        var criteria = new ArrayList<Criteria>();
        CriteriaUtils.gteAndLte(
            filters.getAmountMin(),
            filters.getAmountMax(),
            "amount",
            criteria::add
        );
        CriteriaUtils.gteAndLte(
                filters.getCreatedDateMin(),
                filters.getCreatedDateMax(),
                "createdDate",
                criteria::add
        );
        return orderRepository.find(criteria).map(orderModelConverter::mapFrom);
    }

    @Override
    public Mono<Void> remove(String orderCode) {
        return orderRepository.remove(orderCode)
                .then();
    }

}

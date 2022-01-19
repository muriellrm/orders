package com.lightspace.orders.service;

import com.lightspace.orders.service.model.OrderFiltersModel;
import com.lightspace.orders.service.model.OrderModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderModel> findOne(String orderCode);

    Mono<OrderModel> save(OrderModel orderModel);

    Mono<OrderModel> save(String orderCode, OrderModel orderModel);

    Flux<OrderModel> find(OrderFiltersModel orderFilterModel);

    Mono<Void> remove(String orderCode);
}

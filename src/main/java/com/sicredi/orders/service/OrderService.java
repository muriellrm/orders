package com.sicredi.orders.service;

import com.sicredi.orders.service.model.OrderFilterModel;
import com.sicredi.orders.service.model.OrderModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderModel> findOne(String orderCode);

    Mono<OrderModel> save(OrderModel orderModel);

    Mono<OrderModel> save(String orderCode, OrderModel orderModel);

    Flux<OrderModel> find(OrderFilterModel orderFilterModel);

    Mono<Void> remove(String orderCode);
}

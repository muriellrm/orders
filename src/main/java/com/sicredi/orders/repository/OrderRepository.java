package com.sicredi.orders.repository;

import com.mongodb.client.result.DeleteResult;
import com.sicredi.orders.repository.entity.OrderEntity;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {

    Mono<OrderEntity> save(OrderEntity orderEntity);

    Mono<OrderEntity> findOne(Query q);

    Mono<OrderEntity> findOne(String orderCode);

    Flux<OrderEntity> find(Query q);

    Mono<DeleteResult> remove(String orderCode);
}

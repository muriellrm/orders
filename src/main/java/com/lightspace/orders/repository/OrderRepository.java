package com.lightspace.orders.repository;

import com.mongodb.client.result.DeleteResult;
import com.lightspace.orders.repository.entity.OrderEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface OrderRepository {

    Mono<OrderEntity> save(OrderEntity orderEntity);

    Mono<OrderEntity> findOne(Query q);

    Mono<OrderEntity> findOne(String orderCode);

    Flux<OrderEntity> find(Collection<Criteria> criteriaList);

    Mono<DeleteResult> remove(String orderCode);
}

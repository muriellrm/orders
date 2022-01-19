package com.lightspace.orders.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.lightspace.orders.repository.OrderDAO;
import com.lightspace.orders.repository.OrderRepository;
import com.lightspace.orders.repository.entity.OrderEntity;
import com.lightspace.util.CriteriaUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDAO orderDAO;
    private final ReactiveMongoTemplate reactiveMongoTemplate; // É responsavel para fazer consultar diretas no database.

    public OrderRepositoryImpl(OrderDAO orderDAO, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.orderDAO = orderDAO;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<OrderEntity> save(OrderEntity orderEntity) {
        return orderDAO.save(orderEntity);
    }

    @Override
    public Mono<OrderEntity> findOne(Query q) {
        return reactiveMongoTemplate.findOne(q, OrderEntity.class);
    }

    @Override
    public Mono<OrderEntity> findOne(String orderCode) {
        var query = new Query();
        query.addCriteria(Criteria.where("orderCode").is(orderCode));
        return findOne(query);
    }

    @Override
    public Flux<OrderEntity> find(Collection<Criteria> criteriaList) {
        return reactiveMongoTemplate.find(CriteriaUtils.toQuery(criteriaList), OrderEntity.class);
    }

    @Override
    public Mono<DeleteResult> remove(String orderCode) {
        var query = new Query();
        query.addCriteria(Criteria.where("orderCode").is(orderCode));
        return reactiveMongoTemplate.remove(query, OrderEntity.class);
    }
}

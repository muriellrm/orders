package com.sicredi.orders.repository;

import com.sicredi.orders.repository.entity.OrderEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

// O DAO ou Data Access Object - É uma classe que vai acessar os dados da entidade orders
public interface OrderDAO extends ReactiveMongoRepository<OrderEntity, String> {
}

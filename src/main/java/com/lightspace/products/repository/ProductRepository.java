package com.lightspace.products.repository;


import com.lightspace.products.repository.entity.ProductEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface ProductRepository {


    Flux<ProductEntity> find(Collection<Criteria> criteria);
}
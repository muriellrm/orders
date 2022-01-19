package com.lightspace.products.repository.impl;

import com.lightspace.orders.repository.entity.OrderEntity;
import com.lightspace.products.repository.ProductRepository;
import com.lightspace.products.repository.entity.ProductEntity;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate; // É responsavel para fazer consultar diretas no database.

    public ProductRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<ProductEntity> find(Collection<Criteria> criteriaList) {

        // unwind
        UnwindOperation unwindOperation = Aggregation.unwind("items");
        // group
        GroupOperation sumAmount = Aggregation.group("items.productId")
                .sum("items.amount")
                .as("amountOrder")
                .sum("items.quantity")
                .as("quantityOrder")
                .max("createdDate")
                .as("lastOrderDate");
        // project
        ProjectionOperation projectionOperation = Aggregation
                .project().andExclude("_id")
                .and("amountOrder").as("amountOrder")
                .and("quantityOrder").as("quantityOrder")
                .and("lastOrderDate").as("lastOrderDate")
                .and("_id").as("productId");
        // add all the stages

        var aggregationOperations = new ArrayList<AggregationOperation>();

        if (!CollectionUtils.isEmpty(criteriaList)){
            criteriaList.stream()
                    .map(MatchOperation::new)
                    .forEach(aggregationOperations::add);
        }

        aggregationOperations.add(unwindOperation);
        aggregationOperations.add(sumAmount);
        aggregationOperations.add(projectionOperation);

        TypedAggregation<OrderEntity> skillTypedAggregation = Aggregation.newAggregation(
                OrderEntity.class,
                aggregationOperations
        );
        return reactiveMongoTemplate.aggregate(skillTypedAggregation, ProductEntity.class);
    }


}

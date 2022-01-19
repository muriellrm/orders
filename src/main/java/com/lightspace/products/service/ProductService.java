package com.lightspace.products.service;


import com.lightspace.products.service.model.ProductModel;
import com.lightspace.products.service.model.ProductFiltersModel;
import reactor.core.publisher.Flux;

public interface ProductService {
    Flux<ProductModel> find(ProductFiltersModel filters);
}

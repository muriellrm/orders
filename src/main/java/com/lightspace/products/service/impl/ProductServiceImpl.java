package com.lightspace.products.service.impl;

import com.lightspace.products.repository.ProductRepository;
import com.lightspace.products.service.ProductService;
import com.lightspace.products.service.model.ProductModel;
import com.lightspace.products.service.model.ProductFiltersModel;
import com.lightspace.util.CriteriaUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {

        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Flux<ProductModel> find(ProductFiltersModel filters) {
        var criteria = new ArrayList<Criteria>();


        var createdDateMin =filters.getCreatedDateMin();
        var createdDateMax =filters.getCreatedDateMax();


        CriteriaUtils.gteAndLte(createdDateMin,createdDateMax,"createdDate",criteria::add);
        return productRepository.find(criteria).map(entity->modelMapper.map(entity,ProductModel.class));
    }
}

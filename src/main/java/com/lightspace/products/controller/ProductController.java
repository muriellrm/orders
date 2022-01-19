package com.lightspace.products.controller;

import com.lightspace.products.service.ProductService;
import com.lightspace.products.service.model.ProductModel;
import com.lightspace.products.service.model.ProductFiltersModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public Flux<ProductModel> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date createdDateMax,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date createdDateMin
    ) {

        return productService.find(ProductFiltersModel.builder()
                .createdDateMax(createdDateMax)
                .createdDateMin(createdDateMin)
                .build()
        );
    }

}

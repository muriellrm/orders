package com.lightspace.orders.controller;


import com.lightspace.orders.service.OrderService;
import com.lightspace.orders.service.model.OrderFiltersModel;
import com.lightspace.orders.service.model.OrderModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public Mono<OrderModel> create(@RequestBody OrderModel orderModel) {
        return orderService.save(orderModel);
    }

    @PutMapping("{orderCode}")
    public Mono<OrderModel> put(@PathVariable String orderCode, @RequestBody OrderModel orderModel) {
        return orderService.save(orderCode, orderModel);
    }

    @GetMapping("{orderCode}")
    public Mono<OrderModel> get(@PathVariable String orderCode) {
        return orderService.findOne(orderCode);
    }

    @GetMapping()
    public Flux<OrderModel> getAll(
            @RequestParam(required = false) Double amountMax,
            @RequestParam(required = false) Double amountMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date createdDateMax,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date createdDateMin
    ) {
        return orderService.find(OrderFiltersModel.builder()
                .amountMax(amountMax)
                .amountMin(amountMin)
                .createdDateMax(createdDateMax)
                .createdDateMin(createdDateMin)
                .build());
    }

    @DeleteMapping("{orderCode}")
    public Mono<Void> delete(@PathVariable String orderCode) {
        return orderService.remove(orderCode);
    }

}

package com.sicredi.orders.controller;


import com.sicredi.orders.service.OrderService;
import com.sicredi.orders.service.model.OrderFilterModel;
import com.sicredi.orders.service.model.OrderModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {
        return orderService.find(OrderFilterModel.builder()
                .priceMax(priceMax)
                .priceMin(priceMin)
                .build());
    }

    @DeleteMapping("{orderCode}")
    public Mono<Void> delete(@PathVariable String orderCode) {
        return orderService.remove(orderCode);
    }

}

package com.sicredi.orders.service.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class OrderFilterModel {

    private Double priceMin;
    private Double priceMax;

}

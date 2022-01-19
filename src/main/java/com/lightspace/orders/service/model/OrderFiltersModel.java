package com.lightspace.orders.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(toBuilder = true)
public class OrderFiltersModel {

    private Double amountMin;
    private Double amountMax;
    private Date createdDateMin;
    private Date createdDateMax;

}

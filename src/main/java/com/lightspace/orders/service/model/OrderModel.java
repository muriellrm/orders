package com.lightspace.orders.service.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderModel {
    private String orderCode;
    private List<OrderItemModel> items;
    private Double amount;

    private Date createdDate;
    private Date lastModifiedDate;
}

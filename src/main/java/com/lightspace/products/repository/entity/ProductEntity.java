package com.lightspace.products.repository.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductEntity {
    private Double amountOrder;
    private Double quantityOrder;
    private Date lastOrderDate;
    private String productId;
}

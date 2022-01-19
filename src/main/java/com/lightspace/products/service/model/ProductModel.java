package com.lightspace.products.service.model;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductModel {
    private Double amountOrder;
    private Double quantityOrder;
    private Date lastOrderDate;
    private String productId;


}

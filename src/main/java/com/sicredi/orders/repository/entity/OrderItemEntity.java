package com.sicredi.orders.repository.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderItemEntity {

    private String id;

    private Double unityPrice;

    private Double quantity;

    private Double total;

}

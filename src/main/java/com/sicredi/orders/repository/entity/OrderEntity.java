package com.sicredi.orders.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true, name = "orders_order_code")
    private String orderCode;

    private List<OrderItemEntity> items;

    private Double totalPrice;

}

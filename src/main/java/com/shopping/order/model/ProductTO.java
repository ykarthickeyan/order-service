package com.shopping.order.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductTO {

    private Long id;
    private String description;
    private int inventorySize;
    private String category;
    private BigDecimal price;
}

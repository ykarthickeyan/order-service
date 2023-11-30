package com.shopping.order.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long userId;

    private BigDecimal subtotal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_items_fk", referencedColumnName = "id")
    private List<ProductLineItem> productLineItems;

    public static class CartBuilder {
        private Cart cart;
        public CartBuilder() {
            this.cart = new Cart();
        }

        public CartBuilder withUserId(Long userId) {
            this.cart.setUserId(userId);
            return this;
        }

        public CartBuilder withSubTotal(BigDecimal subTotal) {
            this.cart.setSubtotal(subTotal);
            return this;
        }

        public CartBuilder withProductItems(List<ProductLineItem> productLineItems) {
            this.cart.setProductLineItems(productLineItems);
            return this;
        }

        public Cart build() {
            return this.cart;
        }

    }
}

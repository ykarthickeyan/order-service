package com.shopping.order.controller;

import com.shopping.order.model.Cart;
import com.shopping.order.model.UserOrder;
import com.shopping.order.service.CartService;
import com.shopping.order.service.UserOrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOrderController {

    private final CartService cartService;
    private final UserOrderService userOrderService;

    public UserOrderController(CartService cartService, UserOrderService userOrderService) {
        this.cartService = cartService;
        this.userOrderService = userOrderService;
    }

/**
     (1) Get cart details using the cart Id
     (2) build order using cart details
     (3) save order
 **/
    @PostMapping(value = "/orders/{cartId}")
    public @ResponseBody
    UserOrder ordered(@PathVariable("cartId") Long cartId) {
       Cart cart = cartService.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
       return userOrderService.createOrder(cart);
    }


}

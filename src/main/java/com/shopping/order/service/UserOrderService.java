package com.shopping.order.service;

import com.shopping.order.model.Cart;
import com.shopping.order.model.UserOrder;
import com.shopping.order.repository.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public UserOrderService(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    public UserOrder createOrder(Cart cart) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(cart.getUserId());
        userOrder.setTotal(cart.getSubtotal());
        Date ordered = Date.from(ZonedDateTime.now().toInstant());
        userOrder.setOrdered(ordered);

        return userOrderRepository.save(userOrder);
    }
}

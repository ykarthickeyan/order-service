package com.shopping.order.service;

import com.shopping.order.model.Cart;
import com.shopping.order.model.ProductLineItem;
import com.shopping.order.model.ProductTO;
import com.shopping.order.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addProductToCart(ProductTO product, Long idCart, Long idUser, Integer quantity) {
        Cart cart = cartRepository.findById(idCart).orElse(cartRepository
                .save(createNewCartWithProductItem(product,idUser, quantity)));
        return cart;

    }

    private Cart createNewCartWithProductItem(ProductTO product, Long idUser, Integer quantity) {
        List<ProductLineItem> productLineItemList = new ArrayList<>();
        ProductLineItem productLineItem = new ProductLineItem();
        productLineItem.setProductId(product.getId());
        productLineItem.setPrice(product.getPrice());
        productLineItem.setQuantity(quantity);
        productLineItemList.add(productLineItem);
        return new Cart.CartBuilder()
                .withProductItems(productLineItemList)
                .withSubTotal(product.getPrice())
                .withUserId(idUser)
                .build();
    }

    public Cart createNewCart(Cart cart) {
       Optional<Cart> cartOptional = cartRepository.findByUserId(cart.getUserId());
       if(cartOptional.isPresent()) {
           return cartOptional.get();
       } else {
           return  cartRepository.save(cart);
       }
    }

    public Optional<Cart> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }
}

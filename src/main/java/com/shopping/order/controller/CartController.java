package com.shopping.order.controller;

import com.shopping.order.model.AccountTO;
import com.shopping.order.model.Cart;
import com.shopping.order.model.ProductTO;
import com.shopping.order.service.CartService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CartController {

    private final RestTemplate restTemplate;

    private final CartService cartService;

    public CartController(RestTemplate restTemplate, CartService cartService) {
        this.restTemplate = restTemplate;
        this.cartService = cartService;
    }

    /**
         (1) get user details using user ID from account-service (Client side load balanced)
         (2) if user exists then create new cart
         (3) else throw user does not exist
    **/

    @PostMapping(value = "/users/{idUser}/carts")
    public Cart createCart(@RequestBody Cart cart)  {
        if(cart != null) {
            String accountsUrl = "http://account-service/user/id/" + cart.getUserId().toString();
            AccountTO account = restTemplate.getForObject(accountsUrl, AccountTO.class);
            if (account != null) {
                return cartService.createNewCart(cart);
            }
            else {
                throw new RuntimeException("User account not found");
            }
        }
        else {
            throw new RuntimeException("Cart object is empty");
        }

    }

    /**
        (1) Get user details using user ID from account-service (client side load balanced)
        (2) Get product from product-service by id and check the available quantity
        (3) if user exists then add to cart or create new cart and add to car
        (4) else throw user does not exist

     **/

    @GetMapping(value = "/users/{idUser}/carts/{idCart}")
    public @ResponseBody ProductTO addProduct(@PathVariable("idCart") Long idCart,
                                              @PathVariable("idUser") Long idUser,
                                              @RequestParam("idProduct") Long idProduct,
                                              @RequestParam("quantity") Integer quantity) {


        String accountsUrl = "http://account-service/user/id/"+idUser.toString();
        AccountTO account = restTemplate.getForObject(accountsUrl, AccountTO.class);

        if(account != null) {
            String productUrl = "http://product-service/products/"+idProduct.toString();
            ProductTO product = restTemplate.getForObject(productUrl, ProductTO.class);
            if(product.getInventorySize() > 0 && quantity > 0) {
                cartService.addProductToCart(product, idCart, idUser, quantity);
            }
            return product;
        }
        else {
            throw new RuntimeException("User Account Not found. Please login");
        }
    }

    @GetMapping(value = "/cart/test")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public @ResponseBody ResponseEntity<String> testResourceServer() {
        return new ResponseEntity<>("Success", HttpStatusCode.valueOf(200));
    }
}

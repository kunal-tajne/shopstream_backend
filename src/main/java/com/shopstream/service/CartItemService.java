package com.shopstream.service;


import com.shopstream.exception.CartItemException;
import com.shopstream.exception.UserException;
import com.shopstream.model.Cart;
import com.shopstream.model.CartItem;
import com.shopstream.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
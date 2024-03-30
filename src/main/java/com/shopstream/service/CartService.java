package com.shopstream.service;

import com.shopstream.exception.ProductException;
import com.shopstream.model.Cart;
import com.shopstream.model.CartItem;
import com.shopstream.model.User;
import com.shopstream.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}

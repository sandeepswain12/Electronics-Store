package com.sd.electronicstore.ElectronicStore.services;

import com.sd.electronicstore.ElectronicStore.dtos.AddItemToCartRequest;
import com.sd.electronicstore.ElectronicStore.dtos.CartDto;
import com.sd.electronicstore.ElectronicStore.dtos.ProductDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);
    void removeItemFromCart(String userId,int cartItemId);
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}

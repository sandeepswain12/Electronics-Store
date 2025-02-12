package com.sd.electronicstore.ElectronicStore.dtos;

import com.sd.electronicstore.ElectronicStore.entities.Cart;
import com.sd.electronicstore.ElectronicStore.entities.Product;
import jakarta.persistence.*;

public class CartItemDto {
    private int cartItemId;
    private Product product;
    private int quantity;
    private int totalPrice;

    public CartItemDto() {
    }

    public CartItemDto(int cartItemId, Product product, int quantity, int totalPrice) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

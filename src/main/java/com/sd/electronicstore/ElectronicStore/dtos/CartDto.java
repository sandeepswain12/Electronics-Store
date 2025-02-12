package com.sd.electronicstore.ElectronicStore.dtos;

import com.sd.electronicstore.ElectronicStore.entities.CartItem;
import com.sd.electronicstore.ElectronicStore.entities.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDto {
    private String cartId;
    private Date createdDate;
    private User user;
    private List<CartItem> items = new ArrayList<CartItem>();

    public CartDto() {
    }

    public CartDto(String cartId, Date createdDate, User user, List<CartItem> items) {
        this.cartId = cartId;
        this.createdDate = createdDate;
        this.user = user;
        this.items = items;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}

package com.sd.electronicstore.ElectronicStore.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    private String cartId;
    private Date createdDate;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<CartItem>();

    public Cart() {
    }

    public Cart(String cartId, Date createdDate, User user, List<CartItem> items) {
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

package com.sd.electronicstore.ElectronicStore.dtos;

import com.sd.electronicstore.ElectronicStore.entities.OrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {
    private String orderId;
    private String orderStatus ="PENDING";
    private String paymentStatus ="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveryDate;
    //private User user;
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public OrderDto() {
    }

    public OrderDto(String orderId, String orderStatus, String paymentStatus, int orderAmount, String billingAddress, String billingPhone, String billingName, Date orderDate, Date deliveryDate, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.orderAmount = orderAmount;
        this.billingAddress = billingAddress;
        this.billingPhone = billingPhone;
        this.billingName = billingName;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

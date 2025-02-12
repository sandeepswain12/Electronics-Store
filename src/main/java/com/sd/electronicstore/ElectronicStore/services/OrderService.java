package com.sd.electronicstore.ElectronicStore.services;

import com.sd.electronicstore.ElectronicStore.dtos.CreateCartOrderRequest;
import com.sd.electronicstore.ElectronicStore.dtos.OrderDto;
import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.entities.User;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateCartOrderRequest order);

    void removeOrder(String orderId);

    List<OrderDto> getOrderOfUser(String userId);

    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}

package com.sd.electronicstore.ElectronicStore.repositories;

import com.sd.electronicstore.ElectronicStore.entities.Order;
import com.sd.electronicstore.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}

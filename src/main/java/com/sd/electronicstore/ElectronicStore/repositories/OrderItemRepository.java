package com.sd.electronicstore.ElectronicStore.repositories;

import com.sd.electronicstore.ElectronicStore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

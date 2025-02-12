package com.sd.electronicstore.ElectronicStore.repositories;

import com.sd.electronicstore.ElectronicStore.entities.Cart;
import com.sd.electronicstore.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser(User user);
}

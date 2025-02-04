package com.sd.electronicstore.ElectronicStore.repositories;

import com.sd.electronicstore.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserEmailAndUserPassword(String userEmail, String userPassword);

    List<User> findByUserNameContaining(String keywords);
}

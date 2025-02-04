package com.sd.electronicstore.ElectronicStore.repositories;

<<<<<<< HEAD
=======
import com.sd.electronicstore.ElectronicStore.entities.Category;
>>>>>>> e4b6fe9 (added product entity and mapping the product and category)
import com.sd.electronicstore.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
<<<<<<< HEAD
=======
    Page<Product> findByCategory(Category category, Pageable pageable);
>>>>>>> e4b6fe9 (added product entity and mapping the product and category)
}

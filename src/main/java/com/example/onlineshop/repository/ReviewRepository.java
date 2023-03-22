package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAByProduct(Product product);
}

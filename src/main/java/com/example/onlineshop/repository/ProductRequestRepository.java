package com.example.onlineshop.repository;

import com.example.onlineshop.entity.ProductRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRegistrationRequest,Long> {
}

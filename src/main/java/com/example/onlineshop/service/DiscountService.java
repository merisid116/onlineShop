package com.example.onlineshop.service;

import com.example.onlineshop.entity.Discount;
import com.example.onlineshop.entity.Product;
import java.time.LocalDateTime;
import java.util.List;

public interface DiscountService {
     void create(List<Product> products, Double value, LocalDateTime startDate, LocalDateTime endDate);
     void update(List<Product> products, Double value, LocalDateTime startDate, LocalDateTime endDate);
     List<Discount> getAll();
     Discount getById(Long id);
}

package com.example.onlineshop.service;

import com.example.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    List<Product> getAll();
    void create(Product product);
    void update(Product product, Long id);
    void delete(Long id);
}

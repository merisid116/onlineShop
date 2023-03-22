package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Получение товара по ID
    @Override
    public Product getById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
    // Полный список товаров
    @Override
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    // Создание товара
    @Override
    public void create(Product product){
        productRepository.save(product);
    }
    // Изменения товара
    @Override
    public void update(Product product, Long id) {
        product.setId(id);
        productRepository.save(product);
    }
    // Удаление товара
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}

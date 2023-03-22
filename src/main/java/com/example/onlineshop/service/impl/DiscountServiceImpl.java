package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Discount;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.repository.DiscountRepository;
import com.example.onlineshop.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    //Создание скидки на товары
    @Override
    public void create(List<Product> products, Double value, LocalDateTime startDate, LocalDateTime endDate) {
        Discount discount = new Discount();
        discount.setProducts(products);
        discount.setDiscountAmount(value);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discountRepository.save(discount);
    }
    //Изменение скидок на товар или группу товаров
    @Override
    public void update(List<Product> products, Double value, LocalDateTime startDate, LocalDateTime endDate) {
        Discount discount = new Discount();
        discount.setProducts(products);
        discount.setDiscountAmount(value);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discountRepository.save(discount);
    }
    // Список всех скидок
    @Override
    public List<Discount> getAll() {
        return discountRepository.findAll();
    }

    // Получение скидки
    @Override
    public Discount getById(Long id) {
        return discountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discount not found"));
    }
}

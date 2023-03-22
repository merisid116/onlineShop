package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.PurchaseRepository;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.getReferenceById(id);
    }

    // Получение списка покупок пользователя
    @Override
    public List<Purchase> getPurchaseHistory(User user) {
        return user.getPurchases();
    }
    @Override
    //Создать покупку
    public void create(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    // Возврат товара
    @Override
    public void returnPurchase(Long userId, Long purchaseId) throws RuntimeException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new EntityNotFoundException("Purchase not found"));
        // Проверяем, что прошло менее 24 часов с момента покупки
        if (ChronoUnit.HOURS.between(purchase.getPurchaseDate(), LocalDateTime.now()) > 24) {
            throw new RuntimeException("Return time has expired");
        }
        // Возвращаем деньги пользователю и удаляем покупку из списка его покупок
        user.setBalance(user.getBalance() + purchase.getProduct().getPrice());
        user.getPurchases().remove(purchase);
        userRepository.save(user);
        // Увеличиваем количество товара на складе и сохраняем изменения в репозитории
        Product product = purchase.getProduct();
        product.setQuantity(product.getQuantity() + 1);
        productRepository.save(product);
        // Удаляем покупку из списка всех покупок
        purchaseRepository.delete(purchase);
    }

}

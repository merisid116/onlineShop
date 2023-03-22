package com.example.onlineshop.service;

import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.User;

import java.util.List;

public interface PurchaseService {
    Purchase getPurchaseById(Long id);
    List<Purchase> getPurchaseHistory(User user);
    void create(Purchase purchase);
    void returnPurchase(Long userId, Long purchaseId);

}

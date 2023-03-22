package com.example.onlineshop.service;

import com.example.onlineshop.entity.OrganizationRegistrationRequest;
import com.example.onlineshop.entity.ProductRegistrationRequest;
import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.User;

import java.util.List;

public interface UserService {
    User create(String name, String email, String password);
    User getUserById(Long id);
    List<User> getAllUsers();
    void savePurchase(Purchase purchase, User user);
    void update(User updatedUser, long id);
    void deleteUser(Long userId);
    void addOrganizationRegistrationRequest(User user, OrganizationRegistrationRequest request);
    void addProductRegistrationRequest(User user, ProductRegistrationRequest request);
    void updateBalance(Long userId, double amount);
    void freezeUser(Long userId);
    void unfreezeUser(Long userId);
    void sellProduct(Long userId, Long productId);

}

package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.*;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    // Регистрация пользователя
    @Override
    public User create(String name, String email, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    // Получение пользователя по ID
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    // Получение списка всех пользователей
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Сохранение покупки в истории покупок пользователя
    @Override
    public void savePurchase(Purchase purchase, User user) {
        user.getPurchases().add(purchase);
        userRepository.save(user);
    }
    // Изменение пользователя
    @Override
    public void update(User updatedUser, long id) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }
    // Удаление пользователя
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Добавление заявки на регистрацию организации
    @Override
    public void addOrganizationRegistrationRequest(User user, OrganizationRegistrationRequest request) {
        user.getOrganizationRegistrationRequests().add(request);
        userRepository.save(user);
    }

    // Добавление заявки на регистрацию товара
    @Override
    public void addProductRegistrationRequest(User user, ProductRegistrationRequest request) {
        user.getProductRegistrationRequests().add(request);
        userRepository.save(user);
    }
    // Пополнение баланса
    @Override
    public void updateBalance(Long userId, double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }
    // Заморозка пользователя
    @Override
    public void freezeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        user.setIsEnabled(false);
        userRepository.save(user);
    }
    // Разморозка пользователя
    @Override
    public void unfreezeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        user.setIsEnabled(true);
        userRepository.save(user);
    }
    // Пользователь может купить товар. Баланс пользователя уменьшится на стоимость товара
    public void purchaseProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (user.getBalance() < product.getPrice()) {
            throw new RuntimeException("Insufficient balance");
        }
        user.setBalance(user.getBalance() - product.getPrice());
        userRepository.save(user);
    }
    // Пользователь получает выручку с продажи товара с вычетом комиссии 5%;
    @Override
    public void sellProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        if (product.getPrice() > user.getBalance()) {
            throw new IllegalArgumentException("User does not have enough balance to buy the product");
        }
        Double revenue = product.getPrice() * 0.95;
        user.setBalance(user.getBalance() + revenue);
        userRepository.save(user);
    }

}

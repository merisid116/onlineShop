package com.example.onlineshop.webapp.controllers;

import com.example.onlineshop.entity.*;
import com.example.onlineshop.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShopController {

    private final UserServiceImpl userService;
    private final  ProductServiceImpl productService;
    private final PurchaseServiceImpl purchaseService;
    private final ReviewServiceImpl reviewService;
    private final NotificationServiceImpl notificationService;

    @Autowired
    public ShopController(UserServiceImpl userService, ProductServiceImpl productService,
                          PurchaseServiceImpl purchaseService, ReviewServiceImpl reviewService,
                          NotificationServiceImpl notificationService) {
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
        this.notificationService = notificationService;
    }


    // Пользователь может посмотреть все товары
    @GetMapping("/")
    public String showShopPage(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("productList",productService.getAll());
        return "shop";
    }
    // Пользователь может посмотреть конкретный товар
    @GetMapping("/product/{productId}")
    public String showProductPage(Model model, @PathVariable Long productId) {
        Product product = productService.getById(productId);
        model.addAttribute("product", product);
        return "shop";
    }
    // Пользователь может купить товар. Товар сохранится в истории покупок
    @PostMapping("/purchase/{userId}/{productId}")
    public void purchaseProduct(@PathVariable Long userId, @PathVariable Long productId,
                                @ModelAttribute Purchase purchase, @ModelAttribute User user) {
        userService.purchaseProduct(userId, productId);
        userService.savePurchase(purchase,user);
    }
    // Пользователь может посмотреть все свои покупки
    @GetMapping("/purchase")
    public String showUserPurchases(Model model,@RequestParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        List<Purchase> purchases = purchaseService.getPurchaseHistory(user);
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
    // Пользователь может вернуть товар в течение 24 часов
    @PostMapping("/return/{userId}/{purchaseId}")
    public void returnProduct(@PathVariable Long userId, @PathVariable Long purchaseId) {
        purchaseService.returnPurchase(userId,purchaseId);
    }
    // Пользователь может посмотреть уведомления
    @GetMapping("/notification")
    public String showNotificationsPage(Model model,@RequestParam("userId") Long userId ) {
        User user = userService.getUserById(userId);
        List<Notification> notifications = notificationService.getNotifications(user);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
    // Пользователь может отправить запрос на регистрацию организации
    @PostMapping("/organization-registration-requests//{userId}")
    public void createOrganizationRegistrationRequest(@PathVariable Long userId, @RequestBody OrganizationRegistrationRequest request) {
        User user = userService.getUserById(userId);
        userService.addOrganizationRegistrationRequest(user, request);
    }
    // Пользователь может отправить запрос на регистрацию товара
    @PostMapping("/product-registration-requests/{userId}")
    public void createProductRegistrationRequest(@PathVariable Long userId, @RequestBody ProductRegistrationRequest request) {
        User user = userService.getUserById(userId);
        userService.addProductRegistrationRequest(user, request);
    }
    // Получение списка отзывов для товара
    @GetMapping("/product/{productId}/reviews")
    public List<Review> getReviews(@PathVariable Long productId) {
        Product product = productService.getById(productId);
        return product.getReviews();
    }

    @GetMapping("/product/{productId}/average-rating")
    @ResponseBody
    public Double getAverageRating(@PathVariable Long productId) {
        return reviewService.getAverageRating(productId);
    }
    // Пользователь может написать отзыв на купленный товар
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reviews")
    @ResponseBody
    public Review writeReview(
            @RequestParam("user_id") long userId,
            @RequestParam("purchase_id") long purchaseId,
            @RequestParam("text") String text,
            @RequestParam("rating") int rating
    ) {
        User user = userService.getUserById(userId);
        Purchase purchase = purchaseService.getPurchaseById(purchaseId);
        return reviewService.createReview(user, purchase, text, rating);
    }

    // Пользователь может пополнить баланс
    @PostMapping("/balance")
    public String addBalance(@RequestParam("userId") Long userId, @RequestParam("amount") Double amount) {
        userService.updateBalance(userId, amount);
        return "redirect:/user/" + userId;
    }

    //Пользователь получает выручку с продажи товара
    @PostMapping("/product/sell/{userId}/{productId}")
    @PreAuthorize("hasRole('USER')")
    public void revenue(@PathVariable Long userId, @PathVariable Long productId) {
        userService.sellProduct(userId, productId);
    }
}

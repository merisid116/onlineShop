package com.example.onlineshop.webapp.controllers;

import com.example.onlineshop.entity.*;
import com.example.onlineshop.service.impl.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final NotificationServiceImpl notificationService;
    private final DiscountServiceImpl discountService;
    private final UserServiceImpl userService;
    private final OrganizationRequestServiceImpl organizationRequestService;
    private final ProductRequestServiceImpl productRequestService;
    private final OrganizationServiceImpl organizationService;

    public AdminController(NotificationServiceImpl notificationService, DiscountServiceImpl discountService,
                           UserServiceImpl userService,
                           OrganizationRequestServiceImpl organizationRequestService,
                           ProductRequestServiceImpl productRequestService,
                           OrganizationServiceImpl organizationService) {
        this.notificationService = notificationService;
        this.discountService = discountService;
        this.userService = userService;
        this.organizationRequestService = organizationRequestService;
        this.productRequestService = productRequestService;
        this.organizationService = organizationService;
    }

    // Админ может сделать скидку на товар или группу товаров
    @PostMapping("/discount/create")
    public String createDiscount(@ModelAttribute("discount") Discount discount) {
        discountService.create(discount.getProducts(), discount.getDiscountAmount(), discount.getStartDate(), discount.getEndDate());
        return "redirect:/discount";
    }
    // Админ может изменить скидку на товар или группу товаров
    @PostMapping("/discount/edit")
    public String editDiscount(@ModelAttribute("discount") Discount discount) {
        discountService.update(discount.getProducts(), discount.getDiscountAmount(), discount.getStartDate(), discount.getEndDate());
        return "redirect:/discount";
    }
    //Админ может посмотреть всех пользователей
    @GetMapping("/user")
    public String showUserPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // Админ может посмотреть информацию пользователя
    @GetMapping("/user/{userId}")
    public String viewUser(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "/user";
    }
    //Админ может удалить пользователя
    @PostMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/user";
    }
    //Админ может заморозить пользователя
    @PostMapping("/user/{userId}/freeze")
    public String freezeUser(@PathVariable("userId") Long userId) {
        userService.freezeUser(userId);
        return "redirect:/user";
    }
    // Админ может разморозить пользователя
    @PostMapping("/user/{userId}/unfreeze")
    public String unfreezeUser(@PathVariable("userId") Long userId) {
        userService.unfreezeUser(userId);
        return "redirect:/user";
    }
    //Админ может отправить уведомление пользователю
    @PostMapping("user/notification/{userId}")
    public String sendNotification(@PathVariable("userId") Long userId, @RequestParam String message) {
        User user = userService.getUserById(userId);
        notificationService.create(user, message);
        return "redirect:/user";
    }

    // Админ может посмотреть все заявки на регистрацию организации
    @GetMapping("/request")
    public String showAllOrganizationRequests(Model model) {
        List<OrganizationRegistrationRequest> requests = organizationRequestService.getAll();
        model.addAttribute("requests", requests);
        return "requests"; // возвращаем имя представления для отображения заявок
    }

    // Админ может одобрить заявку на регистрацию организации
    @PostMapping("/request/approve-organization/{id}")
    public String approveOrganizationRequest(@PathVariable("id") Long id) {
        organizationRequestService.approvalOrganization(id);
        return "redirect:/request"; // перенаправляем на страницу со списком всех заявок
    }

    // Админ может отклонить заявку на регистрацию организации
    @PostMapping("/request/disapprove-organization/{id}")
    public String disapproveOrganizationRequest(@PathVariable("id") Long id) {
        organizationRequestService.disapprovalOrganization(id);
        return "redirect:/request";
    }
    // Админ может удалить активные организации
    @PostMapping("/organization/delete/{organizationId}")
    public String deleteOrganization(@PathVariable("organizationId") Long organizationId) {
        organizationService.delete(organizationId);
        return "redirect:/organization";
    }
    // Админ может посмотреть все заявки на регистрацию товара
    @GetMapping("/request/product")
    public String showAllProductRequests(Model model) {
        List<ProductRegistrationRequest> requests = productRequestService.getAll();
        model.addAttribute("requests", requests);
        return "requests";
    }

    // Админ может одобрить заявку на регистрацию товара
    @PostMapping("/request/approve/{id}")
    public String approveProductRequest(@PathVariable("id") Long id) {
        productRequestService.approvalProduct(id);
        return "redirect:/request";
    }

    // Админ может отклонить заявку на регистрацию товара
    @PostMapping("/requests/disapprove/{id}")
    public String disapproveProductRequest(@PathVariable("id") Long id) {
        productRequestService.disapprovalProduct(id);
        return "redirect:/request";
    }
    @GetMapping("/organization")
    public String showOrganizationPage(Model model) {
        List<Organization> organizations = organizationService.getAllOrganization();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }
    //Админ может заморозить организацию
    @PostMapping("/organization/{organizationId}/freeze")
    public String freezeOrganization(@PathVariable("organizationId") Long organizationId) {
        organizationService.freezeOrganization(organizationId);
        return "redirect:/organization";
    }
    // Админ может разморозить организацию
    @PostMapping("/organization/{organizationId}/unfreeze")
    public String unfreeOrganization(@PathVariable("organizationId") Long organizationId) {
        organizationService.unfreezeOrganization(organizationId);
        return "redirect:/organization";
    }
}

package com.example.onlineshop.service;

import com.example.onlineshop.entity.ProductRegistrationRequest;
import java.util.List;

public interface ProductRequestService {
    ProductRegistrationRequest getById(Long id);
    void create(ProductRegistrationRequest productRegistrationRequest);
    List<ProductRegistrationRequest> getAll();
    void approvalProduct(Long requestId);
    void disapprovalProduct(Long requestId);
}

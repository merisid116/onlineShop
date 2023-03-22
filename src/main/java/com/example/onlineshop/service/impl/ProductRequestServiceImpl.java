package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.ProductRegistrationRequest;
import com.example.onlineshop.repository.ProductRequestRepository;
import com.example.onlineshop.service.ProductRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductRequestServiceImpl implements ProductRequestService {

    private final ProductRequestRepository requestRepository;

    @Autowired
    public ProductRequestServiceImpl(ProductRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    // Получение запроса по ID
    @Override
    public ProductRegistrationRequest getById(Long id){
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
    }
    // Создать запрос
    @Override
    public void create(ProductRegistrationRequest productRegistrationRequest){
        requestRepository.save(productRegistrationRequest);
    }

    @Override
    public List<ProductRegistrationRequest> getAll() {
        return requestRepository.findAll();
    }

    // Одобрение запроса
    @Override
    public void approvalProduct(Long requestId) {
        ProductRegistrationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new UsernameNotFoundException("Request not found with id: " + requestId));
        request.setApproved(true);
        requestRepository.save(request);
    }
    // Блокировка запроса
    @Override
    public void disapprovalProduct(Long requestId) {
        ProductRegistrationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new UsernameNotFoundException("Request not found with id: " + requestId));
        request.setApproved(false);
        requestRepository.save(request);
    }
}

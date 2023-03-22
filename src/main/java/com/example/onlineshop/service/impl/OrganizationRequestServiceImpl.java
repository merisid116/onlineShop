package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.OrganizationRegistrationRequest;
import com.example.onlineshop.repository.OrganizationRequestRepository;
import com.example.onlineshop.service.OrganizationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrganizationRequestServiceImpl implements OrganizationRequestService {

    private  final OrganizationRequestRepository repository;
    @Autowired
    public OrganizationRequestServiceImpl(OrganizationRequestRepository repository) {
        this.repository = repository;
    }
    public void create(OrganizationRegistrationRequest organizationRegistrationRequest){
        repository.save(organizationRegistrationRequest);
    }
    //Получить запрос по ID
    @Override
    public OrganizationRegistrationRequest getById(Long id){
        return  repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Request not found"));
    }
    // Список всех запросов
    @Override
    public List<OrganizationRegistrationRequest> getAll(){
        return repository.findAll();
    }

    // Одобрение запроса
    public void approvalOrganization(Long requestId) {
        OrganizationRegistrationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new UsernameNotFoundException("Request not found with id: " + requestId));
        request.setApproved(true);
        repository.save(request);
    }
    // Блокировка запроса
    public void disapprovalOrganization(Long requestId) {
        OrganizationRegistrationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new UsernameNotFoundException("Request not found with id: " + requestId));
        request.setApproved(false);
        repository.save(request);
    }
}

package com.example.onlineshop.service;

import com.example.onlineshop.entity.OrganizationRegistrationRequest;
import java.util.List;

public interface OrganizationRequestService {
    OrganizationRegistrationRequest getById(Long id);
    List<OrganizationRegistrationRequest> getAll();
    void approvalOrganization(Long requestId);
    void disapprovalOrganization(Long requestId);
}

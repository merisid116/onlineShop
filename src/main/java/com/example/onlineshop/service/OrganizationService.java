package com.example.onlineshop.service;

import com.example.onlineshop.entity.Organization;
import java.util.List;

public interface OrganizationService {
    List<Organization> getAllOrganization();
    void create(Organization org);
    void update(Organization org, Long id);
    void delete(Long organizationId);
    void freezeOrganization(Long organizationId);
    void unfreezeOrganization(Long organizationId);
}

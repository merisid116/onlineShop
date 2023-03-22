package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Organization;
import com.example.onlineshop.repository.OrganizationRepository;
import com.example.onlineshop.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    //  Изменение организации
    public void update(Organization org, Long id){
        org.setId(id);
        organizationRepository.save(org);
    }
    // Получение списка организаций
    @Override
    public List<Organization> getAllOrganization() {
        return organizationRepository.findAll();
    }

    // Создание организации
    @Override
    public void create(Organization org){
        organizationRepository.save(org);
    }

    //Удаление организации
    @Override
    public void delete(Long organizationId) {
        organizationRepository.deleteById(organizationId);
    }

    // Заморозка организации
    @Override
    public void freezeOrganization(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with id: " + organizationId));
        organization.setIsEnabled(false);
        organizationRepository.save(organization);
    }

    // Разморозка организации
    @Override
    public void unfreezeOrganization(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with id: " + organizationId));
        organization.setIsEnabled(true);
        organizationRepository.save(organization);
    }
}

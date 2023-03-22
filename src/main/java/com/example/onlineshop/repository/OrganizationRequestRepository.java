package com.example.onlineshop.repository;

import com.example.onlineshop.entity.OrganizationRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRequestRepository extends JpaRepository<OrganizationRegistrationRequest, Long> {
}

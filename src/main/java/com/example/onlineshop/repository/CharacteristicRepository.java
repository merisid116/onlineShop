package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
}

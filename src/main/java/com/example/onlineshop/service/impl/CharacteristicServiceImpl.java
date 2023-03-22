package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Characteristic;
import com.example.onlineshop.repository.CharacteristicRepository;
import com.example.onlineshop.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CharacteristicServiceImpl  implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    @Autowired
    public CharacteristicServiceImpl(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }
    //Создание характеристики
    @Override
    public void create(Characteristic characteristic) {
        characteristicRepository.save(characteristic);
    }
    // Изменение характеристики
    @Override
    public void update(Characteristic characteristic) {
        characteristicRepository.save(characteristic);
    }

}

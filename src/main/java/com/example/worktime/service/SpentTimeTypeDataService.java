package com.example.worktime.service;

import com.example.worktime.entity.SpentTimeType;
import com.example.worktime.repository.SpentTimeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "SpentTimeType"
 */
@Service
@RequiredArgsConstructor
public class SpentTimeTypeDataService {

    private final SpentTimeTypeRepository spentTimeTypeRepository;

    /**
     * Ищет все типы затраченного времени
     *
     * @return список всех типов затраченного времени
     */
    public List<SpentTimeType> findAll() {
        return spentTimeTypeRepository.findAll();
    }

    /**
     * Ищет конкретный тип затраченного времени по id
     *
     * @param idSpentTimeType id типа затраченного времени
     * @return тип затраченного времени
     */
    public Optional<SpentTimeType> findById(int idSpentTimeType) {
        return spentTimeTypeRepository.findById(idSpentTimeType);
    }

}

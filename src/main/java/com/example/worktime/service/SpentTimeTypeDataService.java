package com.example.worktime.service;

import com.example.worktime.entity.SpentTimeType;
import com.example.worktime.repository.SpentTimeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpentTimeTypeDataService {

    private final SpentTimeTypeRepository spentTimeTypeRepository;

    public List<SpentTimeType> findAll() {
        return spentTimeTypeRepository.findAll();
    }

    public Optional<SpentTimeType> findById(int idSpentTimeType) {
        return spentTimeTypeRepository.findById(idSpentTimeType);
    }

}

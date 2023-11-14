package com.example.worktime.service;

import com.example.worktime.entity.DocumentType;
import com.example.worktime.repository.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "DocumentType"
 */
@Service
@RequiredArgsConstructor
public class DocumentTypeDataService {

    private final DocumentTypeRepository documentTypeRepository;

    /**
     * Производит поиск всех типов документов
     *
     * @return список всех типов документов
     */
    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }
}

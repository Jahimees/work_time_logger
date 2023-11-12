package com.example.worktime.service;

import com.example.worktime.entity.DocumentType;
import com.example.worktime.repository.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentTypeDataService {

    private final DocumentTypeRepository documentTypeRepository;

    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }
}

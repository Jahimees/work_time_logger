package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.service.DocumentTypeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest-контроллер для управления типами документов. Только предоставляет их список
 */
@RestController
@RequiredArgsConstructor
public class DocumentTypeRestController {

    private final DocumentTypeDataService documentTypeDataService;

    /**
     * Предоставляет список всех типов документов
     *
     * @return список типов документов
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/document-types")
    public ResponseEntity<List<? extends CustomEntity>> findAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeDataService.findAll());
    }

}

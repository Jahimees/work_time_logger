package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.Document;
import com.example.worktime.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class UserFileRestController {

    private final UploadFileService uploadFileService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/accounts/{idAccount}/user_files")
    public ResponseEntity<CustomEntity> handleFileUpload(@PathVariable int idAccount,
                                                         @RequestParam MultipartFile file,
                                                         @RequestParam(required = false) Integer idDocument) {
        if (!file.isEmpty()) {

            Document document;
            if (idDocument != null && idDocument != 0) {
                document = uploadFileService.uploadDocument(idAccount, idDocument, file);
            } else {
                document = new Document();
                //saveAsImage
            }


            return new ResponseEntity<>(document, HttpStatus.CREATED);
        } else {
            throw new NoSuchElementException("File is empty. User has not chosen it");
        }
    }
}

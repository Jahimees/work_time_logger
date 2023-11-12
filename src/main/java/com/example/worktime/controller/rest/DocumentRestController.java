package com.example.worktime.controller.rest;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.Document;
import com.example.worktime.service.AccountDataService;
import com.example.worktime.service.DocumentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DocumentRestController {

    private final AccountDataService accountDataService;
    private final DocumentDataService documentDataService;

    @PostMapping("/accounts/{idAccount}/documents")
    public ResponseEntity<CustomEntity> createDocument(@RequestBody Document document,
                                                       @PathVariable int idAccount) {

        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.NOT_FOUND.value(),
                    "Account not found"
            ), HttpStatus.NOT_FOUND);
        }

        document.setAccountOwner(accountOptional.get());
        return ResponseEntity.ok(documentDataService.create(document));
    }

    @GetMapping("/accounts/{idAccount}/documents")
    public ResponseEntity<List<? extends CustomEntity>> findByIdAccount(@PathVariable int idAccount) {
       return ResponseEntity.ok(documentDataService.findByIdAccount(idAccount));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/documents")
    public ResponseEntity<List<Document>> findAll() {
        return ResponseEntity.ok(documentDataService.findAll());
    }

    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    @PatchMapping("/documents/{idDocument}")
    public ResponseEntity<Document> patch(@RequestBody Document document,
                                          @PathVariable int idDocument) {

        document.setIdDocument(idDocument);
        return ResponseEntity.ok(documentDataService.patchDocument(document));
    }
}

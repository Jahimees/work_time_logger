package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Document;
import com.example.worktime.entity.Timesheet;
import com.example.worktime.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentDataService {

    private final DocumentRepository documentRepository;
    private final TimesheetDataService timesheetDataService;
    private final AccountDataService accountDataService;

    @Transactional
    public Document create(Document document) {
        document.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        StringBuilder monthYear = new StringBuilder("" + document.getSinceDate().getMonth())
                .append(".")
                .append(document.getSinceDate().getYear() + 1900);

        Account owner = document.getAccountOwner();
        Optional<Timesheet> timesheetOptional =
                timesheetDataService.findByIdAccountAndMonthYear(owner.getIdAccount(), monthYear.toString());

        Timesheet dbTimesheet;
        if (timesheetOptional.isEmpty()) {
            dbTimesheet = new Timesheet();
            dbTimesheet.setAccount(owner);
            dbTimesheet.setMonthYear(monthYear.toString());
            dbTimesheet = timesheetDataService.create(dbTimesheet);
        } else {
            dbTimesheet = timesheetOptional.get();
        }

        document.setTimesheet(dbTimesheet);

        return documentRepository.saveAndFlush(document);
    }

    public Optional<Document> findById(int idDocument) {
        return documentRepository.findById(idDocument);
    }

    public Document update(Document document) {
        return documentRepository.saveAndFlush(document);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document patchDocument(Document document) {
        Optional<Document> documentOptional = findById(document.getIdDocument());

        if (documentOptional.isEmpty()) {
            throw new NotFoundException("Document not found");
        }

        Document docFromDb = documentOptional.get();

        if (document.isConfirmed()) {
            docFromDb.setConfirmed(true);
            docFromDb.setAccountConfirmator(document.getAccountConfirmator());
            docFromDb.setConfirmDate(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            docFromDb.setConfirmed(false);
            docFromDb.setAccountConfirmator(null);
            docFromDb.setConfirmDate(null);
        }

        return documentRepository.saveAndFlush(docFromDb);
    }

    public List<Document> findByIdAccount(int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return documentRepository.findAllByAccountOwner(accountOptional.get());
    }
}

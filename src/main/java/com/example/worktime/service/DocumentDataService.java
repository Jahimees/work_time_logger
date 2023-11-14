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

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Document"
 */
@Service
@RequiredArgsConstructor
public class DocumentDataService {

    private final DocumentRepository documentRepository;
    private final TimesheetDataService timesheetDataService;
    private final AccountDataService accountDataService;

    /**
     * Создает информацию о новом документе в системе.
     *
     * @param document объект документа
     * @return созданный документ
     */
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

    /**
     * Производит поиск документа по id
     *
     * @param idDocument id документа
     * @return найденный документ
     */
    public Optional<Document> findById(int idDocument) {
        return documentRepository.findById(idDocument);
    }

    /**
     * Обновляет документ в базе
     *
     * @param document обновляемый документ
     * @return обновленный документ
     */
    public Document update(Document document) {
        return documentRepository.saveAndFlush(document);
    }

    /**
     * Производит поиск всех документов
     *
     * @return список всех документов
     */
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    /**
     * Частично обновляет документ в системе. А именно служит для подтверждения/снятия подтверждения с документа
     *
     * @param document обновляемый документ
     * @return обновленный документ
     */
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

    /**
     * Производит поиск документов по id пользователя
     *
     * @param idAccount id пользователя
     * @return список всех документов пользователя
     */
    public List<Document> findByIdAccount(int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return documentRepository.findAllByAccountOwner(accountOptional.get());
    }
}

package com.example.worktime.repository;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей document
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    /**
     * Производит поиск всех документов пользователя
     *
     * @param account пользователь
     * @return список документов
     */
    List<Document> findAllByAccountOwner(Account account);
}

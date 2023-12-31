package com.example.worktime.repository;

import com.example.worktime.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей document_type
 */
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
}

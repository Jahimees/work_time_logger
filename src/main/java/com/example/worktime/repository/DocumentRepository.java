package com.example.worktime.repository;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAllByAccountOwner(Account account);
}

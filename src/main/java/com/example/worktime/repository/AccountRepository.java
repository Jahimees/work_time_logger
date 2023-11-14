package com.example.worktime.repository;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей account
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Производит поиск аккаунта по имени пользователя
     *
     * @param username имя пользователя
     */
    Optional<Account> findByUsername(String username);

    /**
     * Производит поиск всех аккаунтов по отделу
     *
     * @param department отдел
     */
    List<Account> findByDepartment(Department department);

    /**
     * Проверяет существование пользователя в базе по имени пользователя
     *
     * @param username имя пользователя
     * @return true - существует, false - иначе
     */
    boolean existsByUsername(String username);
}

package com.example.worktime.repository;

import com.example.worktime.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей department
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    /**
     * Проверяет существование отдела по его имени
     *
     * @param name имя отдела
     * @return true - существует, false - иначе
     */
    boolean existsByName(String name);

    /**
     * Производит поиск отдела по его имени
     *
     * @param name имя отдела
     */
    Optional<Department> findByName(String name);
}

package com.example.worktime.repository;

import com.example.worktime.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей position
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    /**
     * Проверяет существование должности в базе по названию
     *
     * @param name название должности
     * @return true - существует, false - иначе
     */
    boolean existsByName(String name);

    /**
     * Производит поиск должности по названию
     *
     * @param name название должности
     * @return объект должности
     */
    Optional<Position> findByName(String name);
}

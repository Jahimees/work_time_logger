package com.example.worktime.repository;

import com.example.worktime.entity.SpentTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей spent_time_type
 */
@Repository
public interface SpentTimeTypeRepository extends JpaRepository<SpentTimeType, Integer> {
}

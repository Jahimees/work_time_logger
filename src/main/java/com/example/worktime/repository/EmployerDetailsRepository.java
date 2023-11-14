package com.example.worktime.repository;

import com.example.worktime.entity.EmployerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей employer_details
 */
@Repository
public interface EmployerDetailsRepository extends JpaRepository<EmployerDetails, Integer> {
}

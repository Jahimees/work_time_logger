package com.example.worktime.repository;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {

    List<Timesheet> findAllByAccount(Account account);

    Optional<Timesheet> findByAccountAndMonthYear(Account account, String monthYear);
}

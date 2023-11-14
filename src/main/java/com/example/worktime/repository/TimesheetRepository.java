package com.example.worktime.repository;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей timesheet
 */
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {

    /**
     * Производит поиск всех табелей по пользователю
     *
     * @param account пользователь
     * @return список всех табелей
     */
    List<Timesheet> findAllByAccount(Account account);

    /**
     * Производит поиск рабочего табеля по пользователю, а также месяцу и году
     *
     * @param account   пользователь
     * @param monthYear месяц и год в формате 10.2023 (где 10 - это ноябрь, т.к. месяцы идут с 0)
     * @return найденный табель
     */
    Optional<Timesheet> findByAccountAndMonthYear(Account account, String monthYear);
}

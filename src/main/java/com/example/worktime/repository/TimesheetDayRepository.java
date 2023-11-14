package com.example.worktime.repository;

import com.example.worktime.entity.Timesheet;
import com.example.worktime.entity.TimesheetDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей timesheet_day
 */
@Repository
public interface TimesheetDayRepository extends JpaRepository<TimesheetDay, Integer> {

    /**
     * Производит удаление рабочего дня в табеле по табелю и дате
     *
     * @param timesheet табель
     * @param date дата
     */
    void deleteByTimesheetAndDate(Timesheet timesheet, Timestamp date);

    /**
     * Производит поиск всех рабочих дней по табелю
     *
     * @param timesheet табель
     * @return список рабочих дней
     */
    List<TimesheetDay> findAllByTimesheet(Timesheet timesheet);
}

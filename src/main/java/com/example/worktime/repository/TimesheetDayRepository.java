package com.example.worktime.repository;

import com.example.worktime.entity.Timesheet;
import com.example.worktime.entity.TimesheetDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TimesheetDayRepository extends JpaRepository<TimesheetDay, Integer> {

    void deleteByTimesheetAndDate(Timesheet timesheet, Timestamp date);

    List<TimesheetDay> findAllByTimesheet(Timesheet timesheet);
}

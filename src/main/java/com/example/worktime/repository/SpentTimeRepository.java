package com.example.worktime.repository;

import com.example.worktime.entity.SpentTime;
import com.example.worktime.entity.TimesheetDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpentTimeRepository extends JpaRepository<SpentTime, Integer> {

    List<SpentTime> findAllByTimesheetDay(TimesheetDay timesheetDay);
}

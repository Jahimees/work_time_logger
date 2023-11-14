package com.example.worktime.repository;

import com.example.worktime.entity.SpentTime;
import com.example.worktime.entity.TimesheetDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей spent_time
 */
@Repository
public interface SpentTimeRepository extends JpaRepository<SpentTime, Integer> {

    /**
     * Производит поиск всех объектов "Затраченного времени" по конкретному дню в табеле
     *
     * @param timesheetDay рабочий день в табеле
     * @return список затраченного времени на день
     */
    List<SpentTime> findAllByTimesheetDay(TimesheetDay timesheetDay);
}

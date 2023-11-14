package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Timesheet;
import com.example.worktime.entity.TimesheetDay;
import com.example.worktime.repository.TimesheetDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "TimesheetDay"
 */
@Service
@RequiredArgsConstructor
public class TimesheetDayDataService {

    private final TimesheetDataService timesheetDataService;
    private final TimesheetDayRepository timesheetDayRepository;
    private final AccountDataService accountDataService;

    /**
     * Производит поиск рабочего дня табеля по id
     *
     * @param idTimesheetDay id рабочего дня табеля
     * @return рабочий день табеля
     */
    public Optional<TimesheetDay> findById(int idTimesheetDay) {
        return timesheetDayRepository.findById(idTimesheetDay);
    }

    /**
     * Производит поиск всех рабочих дней табеля по id пользователя
     *
     * @param idAccount id пользователя
     * @return список всех рабочих дней пользователя
     */
    public List<TimesheetDay> findAllByIdAccount(int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        List<Timesheet> timesheetList = timesheetDataService.findByIdAccount(idAccount);

        List<TimesheetDay> timesheetDayList = new ArrayList<>();

        timesheetList.forEach(timesheet -> {
            timesheetDayList.addAll(findAllByTimesheet(timesheet));
        });

        return timesheetDayList;
    }

    /**
     * Производит поиск всех рабочих дней по табелю
     *
     * @param timesheet рабочий табель
     * @return список рабочих дней табеля
     */
    public List<TimesheetDay> findAllByTimesheet(Timesheet timesheet) {
        return timesheetDayRepository.findAllByTimesheet(timesheet);
    }

    /**
     * Производит создание всех рабочих дней, которые переданы в параметрах
     *
     * @param timesheetDayList список создаваемых рабочих дней
     * @return список созданных рабочих дней
     */
    public List<TimesheetDay> createAll(List<TimesheetDay> timesheetDayList) {
        timesheetDayList.forEach(timesheetDay -> {
            if (timesheetDay.getStartTime() == null) {
                timesheetDay.setStartTime(new Time(9, 0, 0));
            }
            if (timesheetDay.getEndTime() == null) {
                timesheetDay.setEndTime(new Time(18, 0, 0));
            }
        });

        return timesheetDayRepository.saveAllAndFlush(timesheetDayList);
    }

    /**
     * Удаляет набор рабочих дней из табеля
     *
     * @param timesheet табель с перечнем удаляемых рабочих дней
     */
    @Transactional
    public void deleteAllByTimesheetAndDate(Timesheet timesheet) {
        List<TimesheetDay> timesheetDayList = timesheet.getTimesheetDayList();
        timesheetDayList.forEach(timesheetDay -> timesheetDayRepository.deleteByTimesheetAndDate(timesheet, timesheetDay.getDate()));
    }

}

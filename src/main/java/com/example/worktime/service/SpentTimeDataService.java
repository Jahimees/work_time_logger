package com.example.worktime.service;

import com.example.worktime.entity.SpentTime;
import com.example.worktime.entity.SpentTimeType;
import com.example.worktime.entity.TimesheetDay;
import com.example.worktime.repository.SpentTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "SpentTime"
 */
@Service
@RequiredArgsConstructor
public class SpentTimeDataService {

    private final SpentTimeRepository spentTimeRepository;
    private final TimesheetDayDataService timesheetDayDataService;
    private final SpentTimeTypeDataService spentTimeTypeDataService;

    /**
     * Ищет все объекты затраченного времени по id рабочего дня табеля
     *
     * @param idTimesheetDay id рабочего дня табеля
     * @return список всех объектов затраченного времени
     */
    public List<SpentTime> findAllByIdTimesheetDay(int idTimesheetDay) {
        Optional<TimesheetDay> optionalTimesheetDay = timesheetDayDataService.findById(idTimesheetDay);

        if (optionalTimesheetDay.isEmpty()) {
            throw new NotFoundException("Timesheet day not found");
        }

        List<SpentTime> spentTimeList = spentTimeRepository.findAllByTimesheetDay(optionalTimesheetDay.get());
        spentTimeList.forEach(spentTime -> {
            spentTime.getTimesheetDay();
        });

        return spentTimeList;
    }

    /**
     * Создает новый объект затраченного времени в базе
     *
     * @param spentTime создаваемый объект
     * @return созданный объект затраченного времени
     */
    public SpentTime create(SpentTime spentTime) {
        SpentTime createdSpentTime = spentTimeRepository.saveAndFlush(spentTime);
        createdSpentTime.getTimesheetDay();

        Optional<SpentTimeType> spentTimeTypeOptional =
                spentTimeTypeDataService.findById(createdSpentTime.getSpentTimeType().getIdSpentTimeType());

        if (spentTimeTypeOptional.isEmpty()) {
            throw new NotFoundException("Spent time type not found");
        }

        createdSpentTime.setSpentTimeType(spentTimeTypeOptional.get());

        return createdSpentTime;
    }

    /**
     * Удаляет объект затраченного времени из базы
     *
     * @param idSpentTime id затраченного времени
     */
    public void delete(int idSpentTime) {
        spentTimeRepository.deleteById(idSpentTime);
    }

    /**
     * Ищет все объекты затраченного времени в базе по id пользователя
     *
     * @param idAccount id пользователя
     * @return список всех объектов затраченного времени
     */
    public List<SpentTime> findAllByIdAccount(int idAccount) {

        List<TimesheetDay> timesheetDayList = timesheetDayDataService.findAllByIdAccount(idAccount);

        List<SpentTime> fullSpentTimes = new ArrayList<>();
        timesheetDayList.forEach(timesheetDay -> {
            fullSpentTimes.addAll(spentTimeRepository.findAllByTimesheetDay(timesheetDay));
        });

        return fullSpentTimes;
    }
}

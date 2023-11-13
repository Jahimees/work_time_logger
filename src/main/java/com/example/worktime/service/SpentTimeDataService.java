package com.example.worktime.service;

import com.example.worktime.entity.SpentTime;
import com.example.worktime.entity.SpentTimeType;
import com.example.worktime.entity.TimesheetDay;
import com.example.worktime.repository.SpentTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpentTimeDataService {

    private final SpentTimeRepository spentTimeRepository;
    private final TimesheetDayDataService timesheetDayDataService;
    private final SpentTimeTypeDataService spentTimeTypeDataService;

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

    public void delete(int idSpentTime) {
        spentTimeRepository.deleteById(idSpentTime);
    }
}

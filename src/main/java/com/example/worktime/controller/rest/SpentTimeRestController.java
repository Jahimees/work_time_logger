package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.SpentTime;
import com.example.worktime.entity.TimesheetDay;
import com.example.worktime.service.SpentTimeDataService;
import com.example.worktime.service.TimesheetDayDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest-контроллер для управления запросами к объектам типа "Затраченное время"
 */
@RestController
@RequiredArgsConstructor
public class SpentTimeRestController {

    private final SpentTimeDataService spentTimeDataService;
    private final TimesheetDayDataService timesheetDayDataService;

    /**
     * Производит поиск всех объектов "Затраченное время" по конкретному дню табеля
     *
     * @param idTimesheetDay id дня табеля
     * @return список объектов затраченного времени
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/timesheet-days/{idTimesheetDay}/spent-times")
    public ResponseEntity<List<? extends CustomEntity>> findAllByIdTimesheetDay(
            @PathVariable int idTimesheetDay) {

        return ResponseEntity.ok(spentTimeDataService.findAllByIdTimesheetDay(idTimesheetDay));
    }

    /**
     * Принимает запрос на создание нового объекта о затраченном времени. Доступ только у начальника
     * отдела.
     *
     * @param idTimesheetDay id дня, в котором создается объект
     * @param spentTime создаваемый объект
     * @return созданный объект
     */
    @PreAuthorize("hasRole('BOSS')")
    @PostMapping("/timesheet-days/{idTimesheetDay}/spent-times")
    public ResponseEntity<CustomEntity> create(@PathVariable int idTimesheetDay,
                                               @RequestBody SpentTime spentTime) {
        Optional<TimesheetDay> optionalTimesheetDay = timesheetDayDataService.findById(idTimesheetDay);

        if (optionalTimesheetDay.isEmpty()) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.NOT_FOUND.value(),
                    "Timesheet not found"
            ), HttpStatus.NOT_FOUND);
        }

        spentTime.setTimesheetDay(optionalTimesheetDay.get());

        return ResponseEntity.ok(spentTimeDataService.create(spentTime));
    }

    /**
     * Удаляет затраченное время. Доступно только начальнику отдела
     *
     * @param idSpentTime id затраченного времени
     */
    @PreAuthorize("hasRole('BOSS')")
    @DeleteMapping("/spent-times/{idSpentTime}")
    public ResponseEntity<CustomEntity> delete(@PathVariable int idSpentTime) {
        spentTimeDataService.delete(idSpentTime);

        return new ResponseEntity(new CustomResponseEntity(
                HttpStatus.OK.value(),
                "Spent time deleted"
        ), HttpStatus.OK);
    }

    /**
     * Производит поиск всех объектов затраченного времени конкретного пользователя
     *
     * @param idAccount id пользователя
     * @return список всех объектов затраченного времени
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/accounts/{idAccount}/spent-times")
    public ResponseEntity<List<? extends CustomEntity>> findAllByIdAccount(@PathVariable int idAccount) {
        return ResponseEntity.ok(spentTimeDataService.findAllByIdAccount(idAccount));
    }
}

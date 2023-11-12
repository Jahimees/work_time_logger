package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.Timesheet;
import com.example.worktime.entity.TimesheetDay;
import com.example.worktime.service.TimesheetDataService;
import com.example.worktime.service.TimesheetDayDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TimesheetDayRestController {

    private final TimesheetDataService timesheetDataService;
    private final TimesheetDayDataService timesheetDayDataService;

    @GetMapping("/timesheet-days")
    public ResponseEntity<List<? extends CustomEntity>> findTimesheetDaysByIdAccount(@RequestParam int idAccount) {
        return ResponseEntity.ok(timesheetDayDataService.findAllByIdAccount(idAccount));
    }

    @PostMapping("/timesheet-days")
    public ResponseEntity<CustomEntity> createDays(@RequestBody Timesheet timesheet) {
        Optional<Timesheet> timesheetOptional;
        try {
            timesheetOptional = timesheetDataService.findByIdAccountAndMonthYear(timesheet.getAccount().getIdAccount(), timesheet.getMonthYear());
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.BAD_REQUEST.value(),
                    "Что-то пошло не так..."
            ), HttpStatus.BAD_REQUEST);
        }

        final Timesheet newTimesheet = timesheetOptional.orElseGet(() -> timesheetDataService.create(timesheet));

        List<TimesheetDay> timesheetDayList = timesheet.getTimesheetDayList();

        timesheetDayList.forEach(timesheetDay -> timesheetDay.setTimesheet(newTimesheet));

        timesheetDayDataService.createAll(timesheetDayList);

        newTimesheet.setTimesheetDayList(timesheetDayList);

        return ResponseEntity.ok(newTimesheet);
    }

    @DeleteMapping("/timesheet-days")
    public ResponseEntity<CustomEntity> deleteTimesheetDay(@RequestBody Timesheet timesheet) {
        if (timesheet.getTimesheetDayList().isEmpty()) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.OK.value(),
                    "No objects to delete"
            ), HttpStatus.OK);
        }
        Optional<Timesheet> timesheetOptional;
        try {
            timesheetOptional = timesheetDataService.findByIdAccountAndMonthYear(timesheet.getAccount().getIdAccount(),
                    timesheet.getMonthYear());
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.BAD_REQUEST.value(),
                    "Что-то пошло не так..."
            ), HttpStatus.BAD_REQUEST);
        }

        if (timesheetOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Timesheet timesheetToSend = timesheetOptional.get();
        timesheetToSend.setTimesheetDayList(timesheet.getTimesheetDayList());

        timesheetDayDataService.deleteAllByTimesheetAndDate(timesheetToSend);

        return ResponseEntity.ok().build();
    }
}

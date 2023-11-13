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

@RestController
@RequiredArgsConstructor
public class SpentTimeRestController {

    private final SpentTimeDataService spentTimeDataService;
    private final TimesheetDayDataService timesheetDayDataService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/timesheet-days/{idTimesheetDay}/spent-times")
    public ResponseEntity<List<? extends CustomEntity>> findAllByIdTimesheetDay(
            @PathVariable int idTimesheetDay) {

        return ResponseEntity.ok(spentTimeDataService.findAllByIdTimesheetDay(idTimesheetDay));
    }

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

    @PreAuthorize("hasRole('BOSS')")
    @DeleteMapping("/spent-times/{idSpentTime}")
    public ResponseEntity<CustomEntity> delete(@PathVariable int idSpentTime) {
        spentTimeDataService.delete(idSpentTime);

        return new ResponseEntity(new CustomResponseEntity(
                HttpStatus.OK.value(),
                "Spent time deleted"
        ), HttpStatus.OK);
    }
}

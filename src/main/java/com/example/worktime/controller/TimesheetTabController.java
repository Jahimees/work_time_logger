package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TimesheetTabController {

    @Value("${tab.timesheet}")
    private String TIMESHEET_TAB;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger/{idAccount}/timesheet")
    public String openTimesheetTab(@PathVariable int idAccount) {
        return TIMESHEET_TAB;
    }
}

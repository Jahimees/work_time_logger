package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер, который загружает сам календарь  на странице "рабочее время"
 */
@Controller
public class CalendarController {

    @Value("${part.calendar}")
    private String CALENDAR_PART;

    @GetMapping("/wlogger/calendar")
    public String getCalendar() {
        return CALENDAR_PART;
    }

}

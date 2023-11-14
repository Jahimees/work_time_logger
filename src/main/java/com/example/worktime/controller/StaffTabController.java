package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Контроллер возвращает страницу "Персонал"
 */
@Controller
public class StaffTabController {

    @Value("${tab.staff}")
    private String STAFF_TAB;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/wlogger/{idAccount}/staff")
    public String openStaffTab(@PathVariable int idAccount) {
        return STAFF_TAB;
    }
}

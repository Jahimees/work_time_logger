package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Контроллер возвращающий страницу отдела
 */
@Controller
public class DepartmentTabController {

    @Value("${tab.department}")
    private String DEPARTMENT_TAB;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/wlogger/{idAccount}/department")
    public String openDepartmentTab(@PathVariable int idAccount) {
        return DEPARTMENT_TAB;
    }
}

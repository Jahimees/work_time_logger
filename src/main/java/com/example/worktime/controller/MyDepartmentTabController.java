package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Контроллер возвращает страницу "Мой отдел"
 */
@Controller
public class MyDepartmentTabController {

    @Value("${tab.my_department}")
    private String MY_DEPARTMENT_TAB;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger/{idAccount}/my-department")
    public String openMyDepartmentTab(@PathVariable int idAccount) {
        return MY_DEPARTMENT_TAB;
    }
}

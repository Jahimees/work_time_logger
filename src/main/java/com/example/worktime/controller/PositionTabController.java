package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер возвращает страницу "Должности"
 */
@Controller
public class PositionTabController {

    @Value("${tab.position}")
    private String POSITION_TAB;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/wlogger/{idAccount}/position")
    public String openPositionTab() {
        return POSITION_TAB;
    }
}

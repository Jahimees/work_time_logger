package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SettingTabController {

    @Value("${tab.setting}")
    private String SETTING_TAB;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger/{idAccount}/setting")
    public String openSettingTab(@PathVariable int idAccount) {
        return SETTING_TAB;
    }
}

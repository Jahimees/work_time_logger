package com.example.worktime.controller;

import com.example.worktime.entity.CustomPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Основной контроллер, загружает главное меню
 */
@Controller
public class MainController {

    @Value("${page.main}")
    private String MAIN_PAGE;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger/{idAccount}")
    public String getMainPage(@PathVariable int idAccount) {
        return MAIN_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger")
    public void redirectToAccount(HttpServletResponse httpServletResponse) {
        CustomPrincipal customPrincipal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        httpServletResponse.setHeader("Location", "/wlogger/" + customPrincipal.getIdAccount());
        httpServletResponse.setStatus(302);
    }
}

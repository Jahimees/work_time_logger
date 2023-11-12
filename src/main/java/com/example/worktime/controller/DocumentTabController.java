package com.example.worktime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DocumentTabController {

    @Value("${tab.document}")
    private String DOCUMENT_TAB;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wlogger/{idAccount}/document")
    public String openDocumentTab(@PathVariable int idAccount) {
        return DOCUMENT_TAB;
    }
}

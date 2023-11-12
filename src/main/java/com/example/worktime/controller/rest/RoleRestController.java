package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.service.RoleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleDataService roleDataService;

    @GetMapping("/roles")
    public ResponseEntity<List<? extends CustomEntity>> findAllRoles() {
        return ResponseEntity.ok(roleDataService.findAll());
    }
}

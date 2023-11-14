package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.service.RoleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest-контроллер для управления сущностями типа роль
 */
@RestController
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleDataService roleDataService;

    /**
     * Возвращает все возможные роли
     *
     * @return список ролей
     */
    @GetMapping("/roles")
    public ResponseEntity<List<? extends CustomEntity>> findAllRoles() {
        return ResponseEntity.ok(roleDataService.findAll());
    }
}

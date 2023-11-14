package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.service.SpentTimeTypeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest-контроллер для управления сущностями типа "Тип затрачиваемого времени"
 */
@RestController
@RequiredArgsConstructor
public class SpentTimeTypeRestController {

    private final SpentTimeTypeDataService spentTimeTypeDataService;

    /**
     * Возвращает все типы затрачиваемого времени
     *
     * @return список типов затрачиваемого времени
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/spent-time-types")
    public ResponseEntity<List<? extends CustomEntity>> findAll() {

        return ResponseEntity.ok(spentTimeTypeDataService.findAll());
    }
}

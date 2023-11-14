package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.Department;
import com.example.worktime.service.DepartmentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-контроллер для объектов "отдел"
 */
@RestController
@RequiredArgsConstructor
public class DepartmentRestController {

    private final DepartmentDataService departmentDataService;

    /**
     * Производит поиск всех возможных отделов и отправляет их на клиент
     *
     * @return список отделов
     */
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/departments")
    public ResponseEntity<List<? extends CustomEntity>> findAllDepartments() {

        return ResponseEntity.ok(departmentDataService.findAll());
    }

    /**
     * Создает отдел и отправляет его созданный объект обратно. Только кадровик
     *
     * @param department создаваемый объект
     * @return созданный объект
     */
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/departments")
    public ResponseEntity<CustomEntity> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentDataService.create(department));
    }

    /**
     * Удаляет отдел по id. Только кадровик
     *
     * @param idDepartment id отдела
     */
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/departments/{idDepartment}")
    public void deleteDepartment(@PathVariable int idDepartment) {
        departmentDataService.delete(idDepartment);
    }

    /**
     * Изменение отдела. Только кадровик
     *
     * @param department новый объект отдела
     * @param idDepartment id изменяемого отдела
     * @return измененный отдел
     */
    @PreAuthorize("hasRole('HR')")
    @PatchMapping("/departments/{idDepartment}")
    public ResponseEntity<CustomEntity> patchDepartment(@RequestBody Department department, @PathVariable int idDepartment) {
        department.setIdDepartment(idDepartment);

        return ResponseEntity.ok(departmentDataService.patch(department));
    }
}

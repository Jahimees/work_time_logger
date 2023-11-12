package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.Department;
import com.example.worktime.service.DepartmentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentRestController {

    private final DepartmentDataService departmentDataService;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/departments")
    public ResponseEntity<List<? extends CustomEntity>> findAllDepartments() {

        return ResponseEntity.ok(departmentDataService.findAll());
    }

    @PreAuthorize("hasRole('HR')")
    @PostMapping("/departments")
    public ResponseEntity<CustomEntity> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentDataService.create(department));
    }

    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/departments/{idDepartment}")
    public void deleteDepartment(@PathVariable int idDepartment) {
        departmentDataService.delete(idDepartment);
    }

    @PreAuthorize("hasRole('HR')")
    @PatchMapping("/departments/{idDepartment}")
    public ResponseEntity<CustomEntity> patchDepartment(@RequestBody Department department, @PathVariable int idDepartment) {
        department.setIdDepartment(idDepartment);

        return ResponseEntity.ok(departmentDataService.patch(department));
    }
}

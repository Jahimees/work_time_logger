package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.EmployerDetails;
import com.example.worktime.service.EmployerDetailsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest-контроллер для управления деталями пользователя (фамилия, имя, адрес, телефон и т.д.)
 */
@RestController
@RequiredArgsConstructor
public class EmployerRestController {

    private final EmployerDetailsDataService employerDetailsDataService;

    /**
     * Производит частичное изменение пользовательской информации
     *
     * @param idEmployerDetails id пользовательской информации
     * @param employerDetails обновленный объект пользовательской информации
     * @return полностью обновленный объект из базы данных
     */
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/employer-details/{idEmployerDetails}")
    public ResponseEntity<CustomEntity> patch(@PathVariable int idEmployerDetails,
                                              @RequestBody EmployerDetails employerDetails) {

        employerDetails.setIdEmployerDetails(idEmployerDetails);

        return ResponseEntity.ok(employerDetailsDataService.patch(employerDetails));
    }
}

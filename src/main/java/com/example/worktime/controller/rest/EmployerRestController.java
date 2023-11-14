package com.example.worktime.controller.rest;

import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.EmployerDetails;
import com.example.worktime.service.EmployerDetailsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmployerRestController {

    private final EmployerDetailsDataService employerDetailsDataService;

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/employer-details/{idEmployerDetails}")
    public ResponseEntity<CustomEntity> patch(@PathVariable int idEmployerDetails,
                                              @RequestBody EmployerDetails employerDetails) {

        employerDetails.setIdEmployerDetails(idEmployerDetails);

        return ResponseEntity.ok(employerDetailsDataService.patch(employerDetails));
    }
}

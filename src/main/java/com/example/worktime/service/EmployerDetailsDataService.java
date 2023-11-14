package com.example.worktime.service;

import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.entity.EmployerDetails;
import com.example.worktime.repository.EmployerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployerDetailsDataService {

    private final EmployerDetailsRepository employerDetailsRepository;

    public Optional<EmployerDetails> findById(int idEmployerDetails) {
        return employerDetailsRepository.findById(idEmployerDetails);
    }

    public EmployerDetails create(EmployerDetails employerDetails) {
        return employerDetailsRepository.saveAndFlush(employerDetails);
    }

    public EmployerDetails patch(EmployerDetails employerDetails) {
        Optional<EmployerDetails> employerDetailsOptional = findById(employerDetails.getIdEmployerDetails());

        if (employerDetailsOptional.isEmpty()) {
            throw new NotFoundException("Details not found");
        }
        EmployerDetails employerDetailsFromDb = employerDetailsOptional.get();

        if (employerDetails.getAbout() != null) {
            employerDetailsFromDb.setAbout(employerDetails.getAbout());
        }

        if (employerDetails.getPhone() != null) {
            employerDetailsFromDb.setPhone(employerDetails.getPhone());
        }

        if (employerDetails.getAddress() != null) {
            employerDetailsFromDb.setAddress(employerDetails.getAddress());
        }

        if (employerDetails.getFirstName() != null) {
            employerDetailsFromDb.setFirstName(employerDetails.getFirstName());
        }

        if (employerDetails.getLastName() != null) {
            employerDetailsFromDb.setLastName(employerDetails.getLastName());
        }

        return employerDetailsRepository.saveAndFlush(employerDetailsFromDb);
    }
}

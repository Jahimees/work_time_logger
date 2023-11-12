package com.example.worktime.service;

import com.example.worktime.entity.EmployerDetails;
import com.example.worktime.repository.EmployerDetailsRepository;
import lombok.RequiredArgsConstructor;
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
}

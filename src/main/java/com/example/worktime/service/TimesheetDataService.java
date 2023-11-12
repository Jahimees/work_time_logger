package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Timesheet;
import com.example.worktime.repository.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetDataService {

    private final TimesheetRepository timesheetRepository;
    private final AccountDataService accountDataService;

    public Optional<Timesheet> findById(int idTimesheet) {
        return timesheetRepository.findById(idTimesheet);
    }

    public List<Timesheet> findByIdAccount(int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return timesheetRepository.findAllByAccount(accountOptional.get());
    }

    public Optional<Timesheet> findByIdAccountAndMonthYear(int idAccount, String monthYear) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return timesheetRepository.findByAccountAndMonthYear(accountOptional.get(), monthYear);
    }

    public Timesheet create(Timesheet timesheet) {
        if (timesheet.getMonthYear() == null
                || timesheet.getMonthYear().split("\\.").length != 2) {
            throw new IllegalArgumentException("illegal month year");
        }

        if (timesheet.getAccount() == null) {
            throw new IllegalArgumentException("Account is null");
        }

        return timesheetRepository.saveAndFlush(timesheet);
    }

}

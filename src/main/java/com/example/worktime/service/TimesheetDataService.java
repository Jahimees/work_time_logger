package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Timesheet;
import com.example.worktime.repository.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Timesheet"
 */
@Service
@RequiredArgsConstructor
public class TimesheetDataService {

    private final TimesheetRepository timesheetRepository;
    private final AccountDataService accountDataService;

    /**
     * Ищет табель по id в базе
     *
     * @param idTimesheet id табеля
     * @return найденный табель
     */
    public Optional<Timesheet> findById(int idTimesheet) {
        return timesheetRepository.findById(idTimesheet);
    }

    /**
     * Производит поиск табелей по id пользователя
     *
     * @param idAccount id пользователя
     * @return список всех табелей
     */
    public List<Timesheet> findByIdAccount(int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return timesheetRepository.findAllByAccount(accountOptional.get());
    }

    /**
     * Производит поиск табеля по id пользователя, а также по месяцу и году
     *
     * @param idAccount id пользователя
     * @param monthYear месяц и год в формате 10.2023 (где 10 - ноябрь, т.к. месяцы в системе считаются с 0)
     * @return табель рабочего времени работника определенного месяца определенного года
     */
    public Optional<Timesheet> findByIdAccountAndMonthYear(int idAccount, String monthYear) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return timesheetRepository.findByAccountAndMonthYear(accountOptional.get(), monthYear);
    }

    /**
     * Создает новый табель в системе
     *
     * @param timesheet создаваемый табель
     * @return сохраненный табель в базе
     */
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

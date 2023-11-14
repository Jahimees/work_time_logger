package com.example.worktime.service;

import com.example.worktime.entity.EmployerDetails;
import com.example.worktime.repository.EmployerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "EmployerDetails"
 */
@Service
@RequiredArgsConstructor
public class EmployerDetailsDataService {

    private final EmployerDetailsRepository employerDetailsRepository;

    /**
     * Ищет данные пользователя по id
     *
     * @param idEmployerDetails id данных пользователя
     * @return найденные данные пользователя
     */
    public Optional<EmployerDetails> findById(int idEmployerDetails) {
        return employerDetailsRepository.findById(idEmployerDetails);
    }

    /**
     * Создает новые данные пользователя. Создаются в момент создания аккаунта
     *
     * @param employerDetails данные пользователя
     * @return сохраненные данные пользователя
     */
    public EmployerDetails create(EmployerDetails employerDetails) {
        return employerDetailsRepository.saveAndFlush(employerDetails);
    }

    /**
     * Частично изменяет данные пользователя в базе данных
     *
     * @param employerDetails новые пользовательские данные
     * @return обновленные пользовательские данные
     */
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

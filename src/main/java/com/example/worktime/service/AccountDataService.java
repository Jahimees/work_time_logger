package com.example.worktime.service;

import com.example.worktime.entity.*;
import com.example.worktime.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Account"
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccountDataService {

    private final AccountRepository accountRepository;
    private final EmployerDetailsDataService employerDetailsDataService;
    private final DepartmentDataService departmentDataService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Ищет все аккаунты в базе
     *
     * @return список всех аккаунтов
     */
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * Ищет аккаунт по его id
     *
     * @param idAccount id пользователя
     * @return найденный аккаунт
     */
    public Optional<Account> findById(int idAccount) {
        return accountRepository.findById(idAccount);
    }

    /**
     * Ищет всех пользователей по id отдела
     *
     * @param idDepartment id отдела
     * @return список всех аккаунтов отдела
     */
    public List<Account> findByIdDepartment(int idDepartment) {
        Optional<Department> departmentOptional = departmentDataService.findById(idDepartment);

        if (departmentOptional.isEmpty()) {
            throw new NotFoundException("Department not found");
        }

        return accountRepository.findByDepartment(departmentOptional.get());
    }

    /**
     * Ищет пользователя по его имени пользователя (логину)
     *
     * @param username имя пользователя
     * @return найденный пользователь
     */
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /**
     * Производит изменение параметров аккаунта. Метод используется, когда пользователь
     * сам изменяет свои параметры, а не при помощи кадровика
     *
     * @param accountFromClient новые данные аккаунта
     * @return сохраненный объект аккаунта
     */
    public Account patchSelfAccount(Account accountFromClient) {
        Optional<Account> accountOptional = accountRepository.findById(accountFromClient.getIdAccount());

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account accountDb = accountOptional.get();

        if (accountFromClient.getPosition() != null &&
                !accountFromClient.getPosition().equals(accountDb.getPosition())) {
            accountDb.setPosition(accountFromClient.getPosition());
        }

        if (accountFromClient.getDepartment() != null &&
                !accountFromClient.getDepartment().equals(accountDb.getDepartment())) {
            accountDb.setDepartment(accountFromClient.getDepartment());
        }

        if (accountFromClient.getRole() != null &&
                !accountFromClient.getRole().equals(accountDb.getRole())) {
            accountDb.setRole(accountFromClient.getRole());
        }

        if (accountFromClient.getUsername() != null &&
                !accountFromClient.getUsername().equals(accountDb.getUsername())) {
            if (accountRepository.findByUsername(accountFromClient.getUsername()).isPresent()) {
                throw new AlreadyExistsException("Account with the same username already exists");
            }
            accountDb.setUsername(accountFromClient.getUsername());
        }

        if (accountFromClient.getPassword() != null &&
                !bCryptPasswordEncoder.matches(accountFromClient.getPassword(), accountDb.getPassword())) {
            accountDb.setPassword(bCryptPasswordEncoder.encode(accountFromClient.getPassword()));
        }

        return accountRepository.save(accountDb);
    }

    /**
     * Производит изменение аккаунта. Используется, когда
     * аккаунт изменяет кадровик
     *
     * @param accountFromClient новый объект аккаунта
     * @return измененный объект аккаунта
     */
    public Account patchEmployerAccount(Account accountFromClient) {
        Optional<Account> accountOptional = findById(accountFromClient.getIdAccount());

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account accountFromDb = accountOptional.get();

        if (accountFromClient.getEmployerDetails() != null) {
            EmployerDetails employerDetailsFromClient = accountFromClient.getEmployerDetails();
            EmployerDetails employerDetailsFromDb = accountFromDb.getEmployerDetails();

            if (employerDetailsFromClient.getFirstName() != null
                    && !employerDetailsFromClient.getFirstName()
                    .equals(employerDetailsFromDb.getFirstName())) {
                employerDetailsFromDb.setFirstName(employerDetailsFromClient.getFirstName());
            }

            if (employerDetailsFromClient.getLastName() != null
                    && !employerDetailsFromClient.getLastName().equals(employerDetailsFromDb.getLastName())) {
                employerDetailsFromDb.setLastName(employerDetailsFromClient.getLastName());
            }

            if (employerDetailsFromClient.getPhone() != null
                    && !employerDetailsFromClient.getPhone().equals(employerDetailsFromDb.getPhone())) {
                employerDetailsFromDb.setPhone(employerDetailsFromClient.getPhone());
            }

            if (employerDetailsFromClient.getAddress() != null
                    && !employerDetailsFromClient.getAddress().equals(employerDetailsFromDb.getAddress())) {
                employerDetailsFromDb.setAddress(employerDetailsFromClient.getAddress());
            }
        }

        if (accountFromClient.getDepartment() != null
                && accountFromClient.getDepartment().getIdDepartment()
                != accountFromDb.getDepartment().getIdDepartment()) {

            Department department = new Department();
            department.setIdDepartment(accountFromClient.getDepartment().getIdDepartment());
            accountFromDb.setDepartment(department);
        }

        if (accountFromClient.getRole() != null
                && accountFromClient.getRole().getIdRole() != accountFromDb.getRole().getIdRole()) {

            Role role = new Role();
            role.setIdRole(accountFromClient.getRole().getIdRole());
            accountFromDb.setRole(role);
        }

        if (accountFromClient.getPosition() != null
                && accountFromClient.getPosition().getIdPosition() != accountFromDb.getPosition().getIdPosition()) {

            Position position = new Position();
            position.setIdPosition(accountFromClient.getPosition().getIdPosition());
            accountFromDb.setPosition(position);
        }

        return accountRepository.saveAndFlush(accountFromDb);
    }

    /**
     * Создает аккаунт в базе данных
     *
     * @param account новый аккаунт
     * @return созданный в базе аккаунт
     */
    public Account createAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new AlreadyExistsException("Account with the same username already exists");
        }

        account.setEmployerDetails(employerDetailsDataService.create(account.getEmployerDetails()));

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        return accountRepository.saveAndFlush(account);
    }

    /**
     * Удаляет аккаунт из базы данных по id
     *
     * @param idAccount id аккаунта
     */
    public void deleteAccountById(int idAccount) {
        accountRepository.deleteById(idAccount);
    }
}

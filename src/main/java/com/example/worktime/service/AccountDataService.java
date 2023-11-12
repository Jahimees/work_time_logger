package com.example.worktime.service;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.Department;
import com.example.worktime.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountDataService {

    private final AccountRepository accountRepository;
    private final EmployerDetailsDataService employerDetailsDataService;
    private final DepartmentDataService departmentDataService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(int idAccount) {
        return accountRepository.findById(idAccount);
    }

    public List<Account> findByIdDepartment(int idDepartment) {
        Optional<Department> departmentOptional = departmentDataService.findById(idDepartment);

        if (departmentOptional.isEmpty()) {
            throw new NotFoundException("Department not found");
        }

        return accountRepository.findByDepartment(departmentOptional.get());
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account patchAccount(Account accountFromClient) {
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

    public Account createAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new AlreadyExistsException("Account with the same username already exists");
        }

        account.setEmployerDetails(employerDetailsDataService.create(account.getEmployerDetails()));

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        return accountRepository.saveAndFlush(account);
    }
}

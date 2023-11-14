package com.example.worktime.controller.rest;

import com.example.worktime.entity.Account;
import com.example.worktime.entity.CustomEntity;
import com.example.worktime.entity.CustomPrincipal;
import com.example.worktime.entity.CustomResponseEntity;
import com.example.worktime.service.AccountDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest-контроллер аккаунта. Принимает запросы по обработке данных аккаунта
 */
@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountDataService accountDataService;

    /**
     * Принимает запрос и отправляет данные обо всех аккаунтах на клиент
     *
     * @param department необязательный параметр, если нужно отфильтровать аккаунты по отделу
     * @return список аккаунтов
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/accounts")
    public ResponseEntity<List<? extends CustomEntity>> findAllAccounts(@RequestParam(required = false) Integer department) {
        if (department == null) {
            return ResponseEntity.ok(accountDataService.findAll());
        } else {
            return ResponseEntity.ok(accountDataService.findByIdDepartment(department));
        }
    }

    /**
     * Производит поиск и отправку аккаунта на клиент по его ID
     *
     * @param idAccount идентификатор искомого аккаунта
     * @return найденный аккаунт
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/accounts/{idAccount}")
    public ResponseEntity<CustomEntity> findAccountById(@PathVariable int idAccount) {
        Optional<Account> accountOptional = accountDataService.findById(idAccount);

        if (accountOptional.isEmpty()) {
            return new ResponseEntity<>(new CustomResponseEntity(
                    HttpStatus.NOT_FOUND.value(),
                    "Account not found"
            ), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(accountOptional.get());
    }

    /**
     * Получает запрос на частичное изменение данных аккаунта и возвращает измененные данные
     *
     * @param account новый объект с новыми полями
     * @param idAccount идентификатор аккаунта
     * @return измененный объект
     */
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/accounts/{idAccount}")
    public ResponseEntity<CustomEntity> patchAccount(@RequestBody Account account, @PathVariable int idAccount) {
        CustomPrincipal customPrincipal = (CustomPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (account.getDepartment() != null || account.getPosition() != null) {
            if (!customPrincipal.getRole().getAuthority().equals("ROLE_HR")) {
                return new ResponseEntity<>(new CustomResponseEntity(
                        HttpStatus.FORBIDDEN.value(),
                        "You cant change account"
                ), HttpStatus.FORBIDDEN);
            }
        }

        account.setIdAccount(idAccount);
        Account patchedAccount;
        if (account.getUsername() != null || account.getPassword() != null) {
            if (customPrincipal.getIdAccount() != idAccount) {
                return new ResponseEntity<>(new CustomResponseEntity(
                        HttpStatus.FORBIDDEN.value(),
                        "You cant change another account"
                ), HttpStatus.FORBIDDEN);
            } else {
                patchedAccount = accountDataService.patchSelfAccount(account);
            }
        } else {
            patchedAccount = accountDataService.patchEmployerAccount(account);
        }

        return ResponseEntity.ok(patchedAccount);
    }

    /**
     * Создает аккаунт. Доступно только кадровику
     *
     * @param account создаваемый объект
     * @return созданный объект
     */
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/accounts")
    public ResponseEntity<CustomEntity> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountDataService.createAccount(account));
    }

    /**
     * Удаляет аккаунт по id. Доступно только кадровику
     * @param idAccount id удаляемого аккаунта
     *
     * @return json ответ об удалении
     */
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/accounts/{idAccount}")
    public ResponseEntity<CustomEntity> deleteAccount(@PathVariable int idAccount) {

        accountDataService.deleteAccountById(idAccount);
        return new ResponseEntity<>(new CustomResponseEntity(
                HttpStatus.OK.value(),
                "Account deleted"
        ), HttpStatus.OK);
    }
}

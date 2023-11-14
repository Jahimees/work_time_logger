package com.example.worktime.service;

import com.example.worktime.entity.Role;
import com.example.worktime.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Role"
 */
@Service
@RequiredArgsConstructor
public class RoleDataService {

    private final RoleRepository roleRepository;

    /**
     * Ищет все роли в базе данных
     *
     * @return список всех ролей базы данных
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

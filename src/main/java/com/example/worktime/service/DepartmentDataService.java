package com.example.worktime.service;

import com.example.worktime.entity.Department;
import com.example.worktime.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за бизнес-логику с объектами типа "Department"
 */
@Service
@RequiredArgsConstructor
public class DepartmentDataService {

    private final DepartmentRepository departmentRepository;

    /**
     * Ищет отдел по его id
     *
     * @param idDepartment id отдела
     * @return найденный отдел
     */
    public Optional<Department> findById(int idDepartment) {
        return departmentRepository.findById(idDepartment);
    }

    /**
     * Производит поиск всех отделов
     *
     * @return список всех отделов
     */
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    /**
     * Создает новый отдел в базе
     *
     * @param department новый отдел
     * @return созданный отдел
     */
    public Department create(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new AlreadyExistsException("Department already exists");
        }

        return departmentRepository.saveAndFlush(department);
    }

    /**
     * Изменяет существующий отдел
     *
     * @param department изменяемый отдел
     * @return измененный отдел
     */
    public Department patch(Department department) {
        Optional<Department> departmentOptional = departmentRepository.findByName(department.getName());

        if (department.getName().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be empty");
        }

        if (departmentOptional.isPresent() && departmentOptional.get().getIdDepartment() != department.getIdDepartment()) {
            throw new AlreadyExistsException("This department already exists");
        }

        return departmentRepository.saveAndFlush(department);
    }

    /**
     * Удаляет отдел по его id
     *
     * @param idDepartment id отдела
     */
    public void delete(int idDepartment) {
        departmentRepository.deleteById(idDepartment);
    }
}

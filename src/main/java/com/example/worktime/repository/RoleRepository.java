package com.example.worktime.repository;

import com.example.worktime.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий, который отвечает за непосредственное взаимодействие с базой с таблицей role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

package com.example.worktime.repository;

import com.example.worktime.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    boolean existsByName(String name);

    Optional<Position> findByName(String name);
}

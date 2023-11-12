package com.example.worktime.repository;

import com.example.worktime.entity.SpentTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentTimeTypeRepository extends JpaRepository<SpentTimeType, Integer> {
}

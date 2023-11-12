package com.example.worktime.repository;

import com.example.worktime.entity.SpentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentTimeRepository extends JpaRepository<SpentTime, Integer> {
}

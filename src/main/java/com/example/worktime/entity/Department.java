package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = DEPARTMENT)
public class Department implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_DEPARTMENT)
    private int idDepartment;

    @Column(name = NAME)
    private String name;
}

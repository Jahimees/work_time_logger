package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = SPENT_TIME_TYPE)
public class SpentTimeType implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_SPENT_TIME_TYPE)
    private int idSpentTimeType;

    @Column(name = NAME)
    private String name;

    @Column(name = PAID)
    private boolean isPaid;
}

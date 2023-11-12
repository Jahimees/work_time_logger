package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = POSITION)
public class Position implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_POSITION)
    private int idPosition;

    @Column(name = NAME)
    private String name;
}

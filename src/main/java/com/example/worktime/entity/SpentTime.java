package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = SPENT_TIME)
public class SpentTime implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_SPENT_TIME)
    private int idSpentTime;

    @ManyToOne
    @JoinColumn(name = ID_TIMESHEET_DAY)
    private TimesheetDay timesheetDay;

    @ManyToOne
    @JoinColumn(name = ID_SPENT_TIME_TYPE)
    private SpentTimeType spentTimeType;

    @Column(name = SPENT_TIME)
    private double spentTime;

    @Column(name = CONFIRMED)
    private boolean isConfirmed;
}

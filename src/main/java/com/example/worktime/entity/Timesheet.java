package com.example.worktime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static com.example.worktime.util.DataBaseConstant.*;

/**
 * Сущность, связанная с таблицей timesheet в базе данных
 */
@Data
@Entity(name = TIMESHEET)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="idTimesheet")
public class Timesheet implements CustomEntity {

    @Id
    @Column(name = ID_TIMESHEET)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTimesheet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_ACCOUNT)
    private Account account;

    @Column(name = MONTH_YEAR)
    private String monthYear;

    @OneToMany(mappedBy = TIMESHEET, fetch = FetchType.LAZY)
    @Transient
    private List<TimesheetDay> timesheetDayList;
}

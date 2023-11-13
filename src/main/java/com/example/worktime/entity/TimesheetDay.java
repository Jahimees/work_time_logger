package com.example.worktime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = TIMESHEET_DAY)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="idTimesheetDay")
public class TimesheetDay implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_TIMESHEET_DAY)
    private int idTimesheetDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_TIMESHEET)
    private Timesheet timesheet;

    @Column(name = DATE)
    private Timestamp date;

    @Column(name = START_TIME)
    private Time startTime;

    @Column(name = END_TIME)
    private Time endTime;

}

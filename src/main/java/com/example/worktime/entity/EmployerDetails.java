package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

/**
 * Сущность, связанная с таблицей employer_details в базе данных
 */
@Data
@Entity(name = EMPLOYER_DETAILS)
public class EmployerDetails implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_EMPLOYER_DETAILS)
    private int idEmployerDetails;

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @Column(name = PHONE)
    private String phone;

    @Column(name = ADDRESS)
    private String address;

    @Column(name = PHOTO)
    private String photo;

    @Column(name = ABOUT)
    private String about;
}

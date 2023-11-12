package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = DOCUMENT)
public class Document implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_DOCUMENT)
    private int idDocument;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_TIMESHEET)
    private Timesheet timesheet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_ACCOUNT_CONFIRMATOR)
    private Account accountConfirmator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_ACCOUNT_OWNER)
    private Account accountOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_DOCUMENT_TYPE)
    private DocumentType documentType;

    @Column(name = CREATION_DATE)
    private Timestamp creationDate;

    @Column(name = SINCE_DATE)
    private Timestamp sinceDate;

    @Column(name = TO_DATE)
    private Timestamp toDate;

    @Column(name = CONFIRM_DATE)
    private Timestamp confirmDate;

    @Column(name = CONFIRMED)
    private boolean isConfirmed;

    @Column(name = FILE)
    private String file;

    @Column(name = TEXT)
    private String text;
}

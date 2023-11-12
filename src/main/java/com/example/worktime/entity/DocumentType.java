package com.example.worktime.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.example.worktime.util.DataBaseConstant.*;

@Data
@Entity(name = DOCUMENT_TYPE)
public class DocumentType implements CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_DOCUMENT_TYPE)
    private int idDocumentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ID_SPENT_TIME_TYPE)
    private SpentTimeType spentTimeType;

    @Column(name = NAME)
    private String name;
}

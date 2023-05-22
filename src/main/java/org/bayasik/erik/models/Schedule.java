package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PatientId")
    private Patients patientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersonalId")
    private Personal PersonalId;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Price")
    private double price;
}

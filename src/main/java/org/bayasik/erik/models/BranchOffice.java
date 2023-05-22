package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "OfficeName")
    private String name;

    @Column(name = "OfficeAddress")
    private String address;

    @Column(name = "OfficeBudget")
    private double budget;

}

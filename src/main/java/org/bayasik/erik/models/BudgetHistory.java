package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BudgetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "BudgetAction")
    private double action;

    @Column(name = "BudgetDescription")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchOfficeId")
    private BranchOffice branchOfficeId;
}

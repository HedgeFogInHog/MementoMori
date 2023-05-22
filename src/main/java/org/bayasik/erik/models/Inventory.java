package org.bayasik.erik.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "InventoryName")
    private String name;

    @Column(name = "InventoryAmount")
    private int amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchOfficeId")
    private BranchOffice branchOfficeId;
}
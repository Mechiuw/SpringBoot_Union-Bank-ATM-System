package com.mcsoftware.atm.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_atm")
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "atm_location",nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "branch_id",referencedColumnName = "id")
    @JsonBackReference
    private Branch branch;
}

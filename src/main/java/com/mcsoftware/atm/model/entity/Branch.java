package com.mcsoftware.atm.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name ="branch_name" ,nullable = false)
    private String name;

    @Column(name = "branch_location",nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ATM> atms;

}

package com.marlo.achang.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id @GeneratedValue @Column(name = "CUSTOMER_ID")
    private int customerId;

    @Column
    private String name;
}

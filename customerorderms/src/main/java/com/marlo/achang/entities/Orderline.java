package com.marlo.achang.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "ORDERED_LINES", sequenceName = "ordered_lines")
public class Orderline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordered_lines")
    @Column(name = "ORDERLINE_ID")
    private int orderlineId;

    @Column
    private String productDescription;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "CUSTOMERORDER_ID")
    private CustomerOrder customerOrder;
}

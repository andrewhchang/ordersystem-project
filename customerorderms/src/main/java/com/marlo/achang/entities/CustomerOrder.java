package com.marlo.achang.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue @Column(name = "CUSTOMERORDER_ID")
    private int orderId;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderline> orderLines;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
}

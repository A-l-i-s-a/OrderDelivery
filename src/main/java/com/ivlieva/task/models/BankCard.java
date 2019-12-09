package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "BankCard")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @NotEmpty
    private String validity;

    @Enumerated(EnumType.STRING)
    private NameBankCard name;

    public BankCard() {
    }

    public BankCard(Customer customer, @NotEmpty String validity, NameBankCard name) {
        this.customer = customer;
        this.validity = validity;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public NameBankCard getName() {
        return name;
    }

    public void setName(NameBankCard name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", customer=" + customer.getName() +
                ", validity='" + validity + '\'' +
                ", name=" + name;
    }
}

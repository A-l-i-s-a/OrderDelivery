package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankCard> bankCards;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> ordersList;

    public Customer() {
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        bankCards = new ArrayList<>();
        ordersList = new ArrayList<>();
    }

    public void addBankCard(BankCard bankCard) {
        bankCard.setCustomer(this);
        bankCards.add(bankCard);
    }

    public void removeBankCard(BankCard bankCard) {
        bankCards.remove(bankCard);
    }

    public void addOrders(Orders orders) {
        orders.setCustomer(this);
        ordersList.add(orders);
    }

    public void removeOrders(Orders orders) {
        ordersList.remove(orders);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    public List<Orders> getOrders() {
        return ordersList;
    }

    public void setOrders(List<Orders> orders) {
        this.ordersList = orders;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

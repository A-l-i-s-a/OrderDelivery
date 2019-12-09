package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    private LocalDate timeDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_courier")
    private Courier courier;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> ordersList;

    public Delivery() {
    }

    public Delivery(@NotEmpty String name, @NotEmpty String address, LocalDate timeDelivery, Courier courier) {
        this.name = name;
        this.address = address;
        this.timeDelivery = timeDelivery;
        this.courier = courier;
        ordersList = new ArrayList<>();
    }

    private void addOrders(Orders orders) {
        orders.setDelivery(this);
        ordersList.add(orders);
    }

    private void removeOrders(Orders orders) {
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

    public LocalDate getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(LocalDate timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

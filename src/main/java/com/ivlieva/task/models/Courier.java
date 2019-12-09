package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(max = 10, min = 10)
    private String phoneNumber;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveries;

    public Courier() {
    }

    public Courier(@NotEmpty String name, @NotEmpty @Size(max = 10, min = 10) String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        deliveries = new ArrayList<>();
    }

    public void addDelivery(Delivery delivery) {
        delivery.setCourier(this);
        deliveries.add(delivery);
    }

    public void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

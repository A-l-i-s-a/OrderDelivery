package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String address;

    @NotEmpty
    @Size(min = 10, max = 10)
    private String phoneNumber;

    public Warehouse() {
    }

    public Warehouse(@NotEmpty String address, @NotEmpty @Size(min = 10, max = 10) String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return id +
                ", " + address +
                ", " + phoneNumber;
    }
}

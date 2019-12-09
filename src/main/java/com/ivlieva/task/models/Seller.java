package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @NotEmpty
    @Email
    private String email;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Seller() {
    }

    public Seller(@NotEmpty String name, @NotEmpty @Size(min = 10, max = 10) String phoneNumber, @NotEmpty @Email String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        products = new ArrayList<>();
    }

    private void addProduct (Product product) {
        product.setSeller(this);
        products.add(product);
    }

    private void removeProduct (Product product) {
        products.remove(product);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

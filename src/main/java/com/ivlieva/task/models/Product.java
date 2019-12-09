package com.ivlieva.task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seller")
    private Seller seller;

    @NotEmpty
    private String description;

    @NotEmpty
    private String title;

    @NotEmpty
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "WarehouseContent",
            joinColumns = {@JoinColumn(name = "id_product")},
            inverseJoinColumns = {@JoinColumn(name = "id_warehouse")})
    private Set<Warehouse> warehouseSet;

    public Product() {
    }

    public Product(Seller seller, @NotEmpty String description, @NotEmpty String title, @NotEmpty Double price) {
        this.seller = seller;
        this.description = description;
        this.title = title;
        this.price = price;
        warehouseSet = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Warehouse> getWarehouseSet() {
        return warehouseSet;
    }

    public void setWarehouseSet(Set<Warehouse> warehouseSet) {
        this.warehouseSet = warehouseSet;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}

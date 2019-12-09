package com.ivlieva.task.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_method")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "OrderContents",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_product")})
    private Set<Product> productSet;

    public Orders() {
    }

    public Orders(Customer customer, Delivery delivery, PaymentMethod paymentMethod) {
        this.customer = customer;
        this.delivery = delivery;
        this.paymentMethod = paymentMethod;
        productSet = new HashSet<>();
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", customer=" + customer.getName() +
                ", delivery=" + delivery.getName() +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}

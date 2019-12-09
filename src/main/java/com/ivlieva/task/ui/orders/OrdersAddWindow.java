package com.ivlieva.task.ui.orders;

import com.ivlieva.task.dao.CustomerDAOImpl;
import com.ivlieva.task.dao.DeliveryDAOImpl;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.PaymentMethod;
import com.ivlieva.task.models.Orders;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class OrdersAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<Customer> customer = new ComboBox<>("customer");
    ComboBox<Delivery> delivery = new ComboBox<>("delivery");
    ComboBox<PaymentMethod> paymentMethod = new ComboBox<>("paymentMethod");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Customer> customerServices = new Services<>(new CustomerDAOImpl());
    Services<Delivery> deliveryServices = new Services<>(new DeliveryDAOImpl());

    public OrdersAddWindow(ListDataProvider<Orders> dataProvider, Services<Orders> ordersServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(customer);
        layout.addComponent(delivery);
        layout.addComponent(paymentMethod);

        customer.setItems(customerServices.findAll());
        delivery.setItems(deliveryServices.findAll());

        layout.addComponent(horizontalLayout);

        Binder<Orders> binder = new Binder<>();
        binder.forField(customer).withValidator(new BeanValidator(Orders.class, "customer")).bind(Orders::getCustomer, Orders::setCustomer);
        binder.forField(delivery).withValidator(new BeanValidator(Orders.class, "delivery")).bind(Orders::getDelivery, Orders::setDelivery);
        binder.forField(paymentMethod).withValidator(new BeanValidator(Orders.class, "paymentMethod")).bind(Orders::getPaymentMethod, Orders::setPaymentMethod);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Orders newDoctor = new Orders(customer.getValue(), delivery.getValue(), paymentMethod.getValue());
                ordersServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                paymentMethod.clear();
                delivery.clear();
                customer.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

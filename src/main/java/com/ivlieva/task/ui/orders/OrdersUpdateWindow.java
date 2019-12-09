package com.ivlieva.task.ui.orders;


import com.ivlieva.task.dao.CustomerDAOImpl;
import com.ivlieva.task.dao.DeliveryDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.*;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class OrdersUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<Customer> customer = new ComboBox<>("customer");
    ComboBox<Delivery> delivery = new ComboBox<>("delivery");
    ComboBox<PaymentMethod> paymentMethod = new ComboBox<>("paymentMethod");
    
    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();


    Services<Customer> customerServices = new Services<>(new CustomerDAOImpl());
    Services<Delivery> deliveryServices = new Services<>(new DeliveryDAOImpl());

    public OrdersUpdateWindow(ListDataProvider<Orders> dataProvider, Services<Orders> doctorServices, Orders doctor) {
        super("Update patient");
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

        binder.readBean(doctor);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(doctor);
                        doctorServices.update(doctor);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Orders could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

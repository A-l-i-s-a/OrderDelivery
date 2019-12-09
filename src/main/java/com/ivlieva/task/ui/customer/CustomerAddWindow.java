package com.ivlieva.task.ui.customer;

import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class CustomerAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField address = new TextField("address");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new  SellerDAOImpl());

    public CustomerAddWindow(ListDataProvider<Customer> dataProvider, Services<Customer> productServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(address);

        layout.addComponent(horizontalLayout);

        Binder<Customer> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Customer.class, "name")).bind(Customer::getName, Customer::setName);
        binder.forField(address).withValidator(new BeanValidator(Customer.class, "address")).bind(Customer::getAddress, Customer::setAddress);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Customer newDoctor = new Customer( name.getValue(), address.getValue());
                productServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                name.clear();
                address.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

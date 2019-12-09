package com.ivlieva.task.ui.customer;


import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class CustomerUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField address = new TextField("address");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());

    public CustomerUpdateWindow(ListDataProvider<Customer> dataProvider, Services<Customer> customerServices, Customer customer) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(address);

        layout.addComponent(horizontalLayout);

        Binder<Customer> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Customer.class, "name")).bind(Customer::getName, Customer::setName);
        binder.forField(address).withValidator(new BeanValidator(Customer.class, "address")).bind(Customer::getAddress, Customer::setAddress);

        binder.readBean(customer);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(customer);
                        customerServices.update(customer);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Customer could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

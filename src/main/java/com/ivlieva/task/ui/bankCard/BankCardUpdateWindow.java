package com.ivlieva.task.ui.bankCard;


import com.ivlieva.task.dao.CustomerDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.*;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class BankCardUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<NameBankCard> name = new ComboBox<>("name");
    ComboBox<Customer> customer = new ComboBox<>("customer");
    TextField validity = new TextField("validity");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());

    public BankCardUpdateWindow(ListDataProvider<BankCard> dataProvider, Services<BankCard> doctorServices, BankCard doctor) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);
        layout.addComponent(validity);
        layout.addComponent(name);
        layout.addComponent(customer);

        name.setItems(NameBankCard.values());
        Services<Customer> customerServices = new Services<>(new CustomerDAOImpl());
        customer.setItems(customerServices.findAll());

        layout.addComponent(horizontalLayout);

        Binder<BankCard> binder = new Binder<>();
        binder.forField(validity).withValidator(new BeanValidator(BankCard.class, "validity")).bind(BankCard::getValidity, BankCard::setValidity);
        binder.forField(name).withValidator(new BeanValidator(BankCard.class, "name")).bind(BankCard::getName, BankCard::setName);
        binder.forField(customer).withValidator(new BeanValidator(BankCard.class, "customer")).bind(BankCard::getCustomer, BankCard::setCustomer);

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
                    Notification.show("BankCard could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

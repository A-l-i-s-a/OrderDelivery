package com.ivlieva.task.ui.bankCard;

import com.ivlieva.task.dao.CustomerDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.BankCard;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.models.NameBankCard;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class BankCardAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<NameBankCard> name = new ComboBox<>("name");
    ComboBox<Customer> customer = new ComboBox<>("customer");
    TextField validity = new TextField("validity");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());

    public BankCardAddWindow(ListDataProvider<BankCard> dataProvider, Services<BankCard> productServices) {
        super("Add BankCard");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(validity);
        layout.addComponent(name);
        layout.addComponent(customer);

        name.setItems(NameBankCard.values());
        Services<Customer> customerServices = new Services<>(new  CustomerDAOImpl());
        customer.setItems(customerServices.findAll());

        layout.addComponent(horizontalLayout);

        Binder<BankCard> binder = new Binder<>();
        binder.forField(validity).withValidator(new BeanValidator(BankCard.class, "validity")).bind(BankCard::getValidity, BankCard::setValidity);
        binder.forField(name).withValidator(new BeanValidator(BankCard.class, "name")).bind(BankCard::getName, BankCard::setName);
        binder.forField(customer).withValidator(new BeanValidator(BankCard.class, "customer")).bind(BankCard::getCustomer, BankCard::setCustomer);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                BankCard newDoctor = new BankCard(customer.getValue(), validity.getValue(), name.getValue());
                productServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                validity.clear();
                name.clear();
                customer.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

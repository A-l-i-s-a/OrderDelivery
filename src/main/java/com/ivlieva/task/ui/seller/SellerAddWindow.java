package com.ivlieva.task.ui.seller;

import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class SellerAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField phoneNumber = new TextField("phoneNumber");
    TextField email = new TextField("email");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public SellerAddWindow(ListDataProvider<Seller> dataProvider, Services<Seller> productServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(phoneNumber);
        layout.addComponent(email);

        layout.addComponent(horizontalLayout);

        Binder<Seller> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Seller.class, "name")).bind(Seller::getName, Seller::setName);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Seller.class, "phoneNumber")).bind(Seller::getPhoneNumber, Seller::setPhoneNumber);
        binder.forField(email).withValidator(new BeanValidator(Seller.class, "email")).bind(Seller::getEmail, Seller::setEmail);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Seller newDoctor = new Seller( name.getValue(), phoneNumber.getValue(), email.getValue());
                productServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                name.clear();
                phoneNumber.clear();
                email.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

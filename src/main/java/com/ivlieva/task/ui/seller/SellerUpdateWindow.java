package com.ivlieva.task.ui.seller;


import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class SellerUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField phoneNumber = new TextField("phoneNumber");
    TextField email = new TextField("email");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public SellerUpdateWindow(ListDataProvider<Seller> dataProvider, Services<Seller> doctorServices, Seller doctor) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(phoneNumber);
        layout.addComponent(email);

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(updatePatient);

        Binder<Seller> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Seller.class, "name")).bind(Seller::getName, Seller::setName);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Seller.class, "phoneNumber")).bind(Seller::getPhoneNumber, Seller::setPhoneNumber);
        binder.forField(email).withValidator(new BeanValidator(Seller.class, "email")).bind(Seller::getEmail, Seller::setEmail);

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
                    Notification.show("Seller could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

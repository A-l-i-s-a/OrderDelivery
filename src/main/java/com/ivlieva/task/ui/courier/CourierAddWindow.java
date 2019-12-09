package com.ivlieva.task.ui.courier;

import com.ivlieva.task.dao.DeliveryDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class CourierAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField phoneNumber = new TextField("phoneNumber");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public CourierAddWindow(ListDataProvider<Courier> dataProvider, Services<Courier> courierServices) {
        super("Add courier");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(phoneNumber);

        layout.addComponent(horizontalLayout);

        Binder<Courier> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Courier.class, "name")).bind(Courier::getName, Courier::setName);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Courier.class, "phoneNumber")).bind(Courier::getPhoneNumber, Courier::setPhoneNumber);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Courier newCourier = new Courier( name.getValue(), phoneNumber.getValue());
                courierServices.save(newCourier);
                dataProvider.getItems().add(newCourier);
                dataProvider.refreshAll();
                name.clear();
                phoneNumber.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

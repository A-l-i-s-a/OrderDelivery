package com.ivlieva.task.ui.delivery;

import com.ivlieva.task.dao.CourierDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class DeliveryAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField address = new TextField("address");
    DateField timeDelivery = new DateField("timeDelivery");
    ComboBox<Courier> courier = new ComboBox<>("courier");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Courier> courierServices = new Services<>(new CourierDAOImpl());

    public DeliveryAddWindow(ListDataProvider<Delivery> dataProvider, Services<Delivery> productServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(address);
        layout.addComponent(timeDelivery);
        layout.addComponent(courier);

        courier.setItems(courierServices.findAll());

        layout.addComponent(horizontalLayout);

        Binder<Delivery> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Delivery.class, "name")).bind(Delivery::getName, Delivery::setName);
        binder.forField(address).withValidator(new BeanValidator(Delivery.class, "address")).bind(Delivery::getAddress, Delivery::setAddress);
        binder.forField(timeDelivery).withValidator(new BeanValidator(Delivery.class, "timeDelivery")).bind(Delivery::getTimeDelivery, Delivery::setTimeDelivery);
        binder.forField(courier).withValidator(new BeanValidator(Delivery.class, "courier")).bind(Delivery::getCourier, Delivery::setCourier);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Delivery newDoctor = new Delivery( name.getValue(), address.getValue(), timeDelivery.getValue(), courier.getValue());
                productServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                name.clear();
                address.clear();
                timeDelivery.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

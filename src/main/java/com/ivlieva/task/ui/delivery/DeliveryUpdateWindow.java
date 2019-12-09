package com.ivlieva.task.ui.delivery;


import com.ivlieva.task.dao.CourierDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class DeliveryUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField address = new TextField("address");
    DateField timeDelivery = new DateField("timeDelivery");
    ComboBox<Courier> courier = new ComboBox<>("courier");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Courier> courierServices = new Services<>(new CourierDAOImpl());

    public DeliveryUpdateWindow(ListDataProvider<Delivery> dataProvider, Services<Delivery> doctorServices, Delivery doctor) {
        super("Update patient");
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
                    Notification.show("Delivery could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

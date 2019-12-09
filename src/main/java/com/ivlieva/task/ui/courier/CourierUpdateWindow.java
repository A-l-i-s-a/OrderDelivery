package com.ivlieva.task.ui.courier;


import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.models.Product;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class CourierUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("name");
    TextField phoneNumber = new TextField("phoneNumber");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public CourierUpdateWindow(ListDataProvider<Courier> dataProvider, Services<Courier> courierServices, Courier courier) {
        super("Update courier");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(phoneNumber);

        layout.addComponent(horizontalLayout);

        Binder<Courier> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Courier.class, "name")).bind(Courier::getName, Courier::setName);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Courier.class, "phoneNumber")).bind(Courier::getPhoneNumber, Courier::setPhoneNumber);

        binder.readBean(courier);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(courier);
                        courierServices.update(courier);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Courier could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

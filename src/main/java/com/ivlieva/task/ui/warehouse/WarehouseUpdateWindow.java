package com.ivlieva.task.ui.warehouse;


import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Warehouse;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class WarehouseUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField address = new TextField("address");
    TextField phoneNumber = new TextField("phoneNumber");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();


    public WarehouseUpdateWindow(ListDataProvider<Warehouse> dataProvider, Services<Warehouse> warehouseServices, Warehouse doctor) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        horizontalLayout.addComponent(updatePatient);

        layout.addComponent(address);
        layout.addComponent(phoneNumber);

        layout.addComponent(horizontalLayout);

        Binder<Warehouse> binder = new Binder<>();
        binder.forField(address).withValidator(new BeanValidator(Warehouse.class, "title")).bind(Warehouse::getAddress, Warehouse::setAddress);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Warehouse.class, "price")).bind(Warehouse::getPhoneNumber, Warehouse::setPhoneNumber);

        binder.readBean(doctor);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(doctor);
                        warehouseServices.update(doctor);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Warehouse could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

package com.ivlieva.task.ui.warehouse;

import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Warehouse;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class WarehouseAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField address = new TextField("address");
    TextField phoneNumber = new TextField("phoneNumber");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public WarehouseAddWindow(ListDataProvider<Warehouse> dataProvider, Services<Warehouse> warehouseServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(address);
        layout.addComponent(phoneNumber);

        layout.addComponent(horizontalLayout);

        Binder<Warehouse> binder = new Binder<>();
        binder.forField(address).withValidator(new BeanValidator(Warehouse.class, "title")).bind(Warehouse::getAddress, Warehouse::setAddress);
        binder.forField(phoneNumber).withValidator(new BeanValidator(Warehouse.class, "price")).bind(Warehouse::getPhoneNumber, Warehouse::setPhoneNumber);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Warehouse newDoctor = new Warehouse( address.getValue(), phoneNumber.getValue());
                warehouseServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                address.clear();
                phoneNumber.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

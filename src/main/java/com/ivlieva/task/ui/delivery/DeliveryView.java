package com.ivlieva.task.ui.delivery;

import com.ivlieva.task.dao.DeliveryDAOImpl;
import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.models.Delivery;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class DeliveryView extends VerticalLayout {

    Grid<Delivery> grid = new Grid<>(Delivery.class);

    //Create a button to add a product to the database
    Button add = new Button("Add");

    //Create a button to delete a product from the database
    Button delete = new Button("Delete");

    //Create a button to update product information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DeliveryView() {

        Services<Delivery> deliveryServices = new Services<>(new DeliveryDAOImpl());
        ListDataProvider<Delivery> dataProvider = new ListDataProvider<>(deliveryServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("name", "address", "timeDelivery", "courier");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("ordersList").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new DeliveryAddWindow(dataProvider, deliveryServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Delivery product = null;

            for (Delivery doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                try {
                    deliveryServices.delete(product);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Delivery product = null;

            for (Delivery doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                getUI().addWindow(new DeliveryUpdateWindow(dataProvider, deliveryServices, product));
            } else {
                Notification.show("Select line");
            }
        });
        

        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(update);
        horizontalLayout.addComponent(delete);

        addComponent(horizontalLayout);
    }
}

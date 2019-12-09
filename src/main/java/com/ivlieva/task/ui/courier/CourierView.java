package com.ivlieva.task.ui.courier;

import com.ivlieva.task.dao.CourierDAOImpl;
import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.models.Courier;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class CourierView extends VerticalLayout {

    Grid<Courier> grid = new Grid<>(Courier.class);

    //Create a button to add a courier to the database
    Button add = new Button("Add");

    //Create a button to delete a courier from the database
    Button delete = new Button("Delete");

    //Create a button to update courier information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public CourierView() {

        Services<Courier> courierServices = new Services<>(new CourierDAOImpl());
        ListDataProvider<Courier> dataProvider = new ListDataProvider<>(courierServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("name", "phoneNumber");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("deliveries").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new CourierAddWindow(dataProvider, courierServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Courier courier = null;

            for (Courier doctorFromSet : grid.getSelectedItems()) {
                courier = doctorFromSet;
            }

            if (courier != null) {
                try {
                    courierServices.delete(courier);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Courier courier = null;

            for (Courier doctorFromSet : grid.getSelectedItems()) {
                courier = doctorFromSet;
            }

            if (courier != null) {
                getUI().addWindow(new CourierUpdateWindow(dataProvider, courierServices, courier));
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

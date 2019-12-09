package com.ivlieva.task.ui.warehouse;

import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.dao.WarehouseDAOImpl;
import com.ivlieva.task.models.Warehouse;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class WarehouseView extends VerticalLayout {

    Grid<Warehouse> grid = new Grid<>(Warehouse.class);

    //Create a button to add a warehouse to the database
    Button add = new Button("Add");

    //Create a button to delete a warehouse from the database
    Button delete = new Button("Delete");

    //Create a button to update warehouse information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public WarehouseView() {

        Services<Warehouse> warehouseServices = new Services<>(new WarehouseDAOImpl());
        ListDataProvider<Warehouse> dataProvider = new ListDataProvider<>(warehouseServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("address", "phoneNumber");
        grid.getColumn("id").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new WarehouseAddWindow(dataProvider, warehouseServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Warehouse warehouse = null;

            for (Warehouse doctorFromSet : grid.getSelectedItems()) {
                warehouse = doctorFromSet;
            }

            if (warehouse != null) {
                try {
                    warehouseServices.delete(warehouse);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Warehouse warehouse = null;

            for (Warehouse doctorFromSet : grid.getSelectedItems()) {
                warehouse = doctorFromSet;
            }

            if (warehouse != null) {
                getUI().addWindow(new WarehouseUpdateWindow(dataProvider, warehouseServices, warehouse));
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

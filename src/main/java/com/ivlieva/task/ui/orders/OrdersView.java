package com.ivlieva.task.ui.orders;

import com.ivlieva.task.dao.OrdersDAOImpl;
import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.models.Orders;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class OrdersView extends VerticalLayout {

    Grid<Orders> grid = new Grid<>(Orders.class);

    //Create a button to add a product to the database
    Button add = new Button("Add");

    //Create a button to delete a product from the database
    Button delete = new Button("Delete");

    //Create a button to update product information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public OrdersView() {

        Services<Orders> doctorServices = new Services<>(new OrdersDAOImpl());
        ListDataProvider<Orders> dataProvider = new ListDataProvider<>(doctorServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("customer", "delivery", "paymentMethod");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("productSet").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new OrdersAddWindow(dataProvider, doctorServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Orders product = null;

            for (Orders doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                try {
                    doctorServices.delete(product);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Orders product = null;

            for (Orders doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                getUI().addWindow(new OrdersUpdateWindow(dataProvider, doctorServices, product));
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

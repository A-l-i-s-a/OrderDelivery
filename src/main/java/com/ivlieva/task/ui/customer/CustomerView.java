package com.ivlieva.task.ui.customer;

import com.ivlieva.task.dao.CustomerDAOImpl;
import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.models.Customer;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class CustomerView extends VerticalLayout {

    Grid<Customer> grid = new Grid<>(Customer.class);

    //Create a button to add a product to the database
    Button add = new Button("Add");

    //Create a button to delete a product from the database
    Button delete = new Button("Delete");

    //Create a button to update product information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public CustomerView() {

        Services<Customer> customerServices = new Services<>(new CustomerDAOImpl());
        ListDataProvider<Customer> dataProvider = new ListDataProvider<>(customerServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("name", "address");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("bankCards").setHidden(true);
        grid.getColumn("orders").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new CustomerAddWindow(dataProvider, customerServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Customer product = null;

            for (Customer doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                try {
                    customerServices.delete(product);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Customer product = null;

            for (Customer doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                getUI().addWindow(new CustomerUpdateWindow(dataProvider, customerServices, product));
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

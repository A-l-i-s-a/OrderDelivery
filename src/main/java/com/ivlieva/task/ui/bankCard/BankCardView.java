package com.ivlieva.task.ui.bankCard;

import com.ivlieva.task.dao.BankCardDAOImpl;
import com.ivlieva.task.models.BankCard;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class BankCardView extends VerticalLayout {

    Grid<BankCard> grid = new Grid<>(BankCard.class);

    //Create a button to add a product to the database
    Button add = new Button("Add");

    //Create a button to delete a product from the database
    Button delete = new Button("Delete");

    //Create a button to update product information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public BankCardView() {

        Services<BankCard> doctorServices = new Services<>(new BankCardDAOImpl());
        ListDataProvider<BankCard> dataProvider = new ListDataProvider<>(doctorServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("customer", "validity", "name");
        grid.getColumn("id").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new BankCardAddWindow(dataProvider, doctorServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            BankCard product = null;

            for (BankCard doctorFromSet : grid.getSelectedItems()) {
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
            BankCard product = null;

            for (BankCard doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                getUI().addWindow(new BankCardUpdateWindow(dataProvider, doctorServices, product));
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

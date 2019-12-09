package com.ivlieva.task.ui.seller;

import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class SellerView extends VerticalLayout {

    Grid<Seller> grid = new Grid<>(Seller.class);

    //Create a button to add a seller to the database
    Button add = new Button("Add");

    //Create a button to delete a seller from the database
    Button delete = new Button("Delete");

    //Create a button to update seller information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public SellerView() {

        Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());
        ListDataProvider<Seller> dataProvider = new ListDataProvider<>(sellerServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("name", "phoneNumber", "email");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("products").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new SellerAddWindow(dataProvider, sellerServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Seller seller = null;

            for (Seller doctorFromSet : grid.getSelectedItems()) {
                seller = doctorFromSet;
            }

            if (seller != null) {
                try {
                    sellerServices.delete(seller);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Seller seller = null;

            for (Seller doctorFromSet : grid.getSelectedItems()) {
                seller = doctorFromSet;
            }

            if (seller != null) {
                getUI().addWindow(new SellerUpdateWindow(dataProvider, sellerServices, seller));
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

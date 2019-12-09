package com.ivlieva.task.ui.product;

import com.ivlieva.task.dao.ProductDAOImpl;
import com.ivlieva.task.models.Product;
import com.ivlieva.task.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;


public class ProductView extends VerticalLayout {

    Grid<Product> grid = new Grid<>(Product.class);

    //Create a TextField and ComboBox for class fields
    TextField seller = new TextField("seller");
    TextField description = new TextField("description");
    TextField title = new TextField("title");
    TextField price = new TextField("price");

    //Create a button to add a product to the database
    Button add = new Button("Add");

    //Create a button to delete a product from the database
    Button delete = new Button("Delete");

    //Create a button to update product information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public ProductView() {

        Services<Product> doctorServices = new Services<>(new ProductDAOImpl());
        ListDataProvider<Product> dataProvider = new ListDataProvider<>(doctorServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("title", "price", "seller", "description");
        grid.getColumn("id").setHidden(true);
        grid.getColumn("warehouseSet").setHidden(true);

        addComponent(grid);
        setExpandRatio(grid, 1);


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new ProductAddWindow(dataProvider, doctorServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Product product = null;

            for (Product doctorFromSet : grid.getSelectedItems()) {
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
            Product product = null;

            for (Product doctorFromSet : grid.getSelectedItems()) {
                product = doctorFromSet;
            }

            if (product != null) {
                getUI().addWindow(new ProductUpdateWindow(dataProvider, doctorServices, product));
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

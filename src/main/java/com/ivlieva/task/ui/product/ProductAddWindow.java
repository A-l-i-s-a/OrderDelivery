package com.ivlieva.task.ui.product;

import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Product;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

import javax.print.SimpleDoc;
import java.security.Provider;

public class ProductAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<Seller> seller = new ComboBox<>("seller");
    TextField description = new TextField("description");
    TextField title = new TextField("title");
    TextField price = new TextField("price");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new  SellerDAOImpl());

    public ProductAddWindow(ListDataProvider<Product> dataProvider, Services<Product> productServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(title);
        layout.addComponent(price);
        layout.addComponent(seller);
        layout.addComponent(description);

        seller.setItems(sellerServices.findAll());

        layout.addComponent(horizontalLayout);

        Binder<Product> binder = new Binder<>();
        binder.forField(title).withValidator(new BeanValidator(Product.class, "title")).bind(Product::getTitle, Product::setTitle);
        binder.forField(price).withValidator(new BeanValidator(Product.class, "price")).bind(product -> product.getPrice().toString(), (product, price) -> product.setPrice(Double.parseDouble(price)));
        binder.forField(seller).withValidator(new BeanValidator(Product.class, "seller")).bind(Product::getSeller, Product::setSeller);
        binder.forField(description).withValidator(new BeanValidator(Product.class, "description")).bind(Product::getDescription, Product::setDescription);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Product newDoctor = new Product( seller.getValue(), description.getValue(), title.getValue(), Double.parseDouble(price.getValue()));
                productServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                title.clear();
                price.clear();
                seller.clear();
                description.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

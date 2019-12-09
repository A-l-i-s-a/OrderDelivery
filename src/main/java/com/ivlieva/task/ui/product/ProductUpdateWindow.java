package com.ivlieva.task.ui.product;


import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Product;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

public class ProductUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    ComboBox<Seller> seller = new ComboBox<>("seller");
    TextField description = new TextField("description");
    TextField title = new TextField("title");
    TextField price = new TextField("price");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());

    public ProductUpdateWindow(ListDataProvider<Product> dataProvider, Services<Product> doctorServices, Product doctor) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(title);
        layout.addComponent(price);
        layout.addComponent(seller);
        layout.addComponent(description);

        seller.setItems(sellerServices.findAll());

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(updatePatient);

        Binder<Product> binder = new Binder<>();
        binder.forField(title).withValidator(new BeanValidator(Product.class, "title")).bind(Product::getTitle, Product::setTitle);
        binder.forField(price).withValidator(new BeanValidator(Product.class, "price")).bind(product -> product.getPrice().toString(), (product, price) -> product.setPrice(Double.parseDouble(price)));
        binder.forField(seller).withValidator(new BeanValidator(Product.class, "seller")).bind(Product::getSeller, Product::setSeller);
        binder.forField(description).withValidator(new BeanValidator(Product.class, "description")).bind(Product::getDescription, Product::setDescription);

        binder.readBean(doctor);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(doctor);
                        doctorServices.update(doctor);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Product could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}

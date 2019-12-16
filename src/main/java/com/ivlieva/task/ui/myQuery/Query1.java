package com.ivlieva.task.ui.myQuery;

import com.ivlieva.task.dao.SellerDAOImpl;
import com.ivlieva.task.models.Seller;
import com.ivlieva.task.services.Services;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class Query1 extends VerticalLayout {
    Button button = new Button("Update");
    Grid<Q1> q1Grid = new Grid<>();

    public Query1() {
        Services<Seller> sellerServices = new Services<>(new SellerDAOImpl());
        final List<Q1>[] list = new List[]{sellerServices.q1()};
        button.addClickListener(clickEvent -> list[0] = sellerServices.q1());
        q1Grid.setItems(list[0]);
        q1Grid.addColumn(Q1::getName);
        q1Grid.addColumn(Q1::getSum);
        addComponent(button);
        addComponent(q1Grid);
    }
}

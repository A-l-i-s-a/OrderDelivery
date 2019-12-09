package com.ivlieva.task.ui;

import com.ivlieva.task.ui.bankCard.BankCardView;
import com.ivlieva.task.ui.courier.CourierView;
import com.ivlieva.task.ui.customer.CustomerView;
import com.ivlieva.task.ui.delivery.DeliveryView;
import com.ivlieva.task.ui.orders.OrdersView;
import com.ivlieva.task.ui.product.ProductView;
import com.ivlieva.task.ui.seller.SellerView;
import com.ivlieva.task.ui.warehouse.WarehouseView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class DataView extends VerticalLayout {
    public DataView() {
        TabSheet tabsheet = new TabSheet();
        addComponent(tabsheet);

        tabsheet.addTab(new BankCardView(), "BankCard");
        tabsheet.addTab(new CourierView(), "Courier");
        tabsheet.addTab(new CustomerView(), "Customer");
        tabsheet.addTab(new DeliveryView(), "Delivery");
        tabsheet.addTab(new OrdersView(), "Orders");
        tabsheet.addTab(new ProductView(), "Product");
        tabsheet.addTab(new SellerView(), "Seller");
        tabsheet.addTab(new WarehouseView(), "Warehouse");


    }
}

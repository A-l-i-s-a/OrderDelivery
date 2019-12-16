package com.ivlieva.task.ui;

import com.ivlieva.task.ui.myQuery.Query1;
import com.ivlieva.task.ui.myQuery.Query2;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        setContent(layout);


        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        tabsheet.addTab(new DataView(), "Data");
        tabsheet.addTab(new Query1(), "Query1");
        tabsheet.addTab(new Query2(), "Query2");

    }
}
package com.uzh.tempic.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;

import java.util.ArrayList;
import java.util.List;

public class WorldDashboardView extends Composite implements WorldDashboardPresenter.Display {
    private final Button addButton;
    private final Button deleteButton;

    private HorizontalPanel wrapperTable;
    private VerticalPanel navTable;
    private VerticalPanel contentWrapperTable;
    private FlexTable dashboardTable;


    public WorldDashboardView() {

        // SET WHOLE WRAPPER

        // SPLIT BETWEEN NAV AND CONTENT
        wrapperTable = new HorizontalPanel();
        initWidget(wrapperTable);
        wrapperTable.setWidth("100%");
        wrapperTable.getElement().setId("tempic_wrapper");

        // CREATE NAV AND ADD TO TEMPIC_WRAPPER
        navTable = new VerticalPanel();
        navTable.getElement().setId("nav");

        Label logo = new Label("Tempic");
        Hyperlink linkDashboard = new Hyperlink("Dashboard", "dashboard");
        Hyperlink linkCountry = new Hyperlink("Country", "country");
        Hyperlink linkWorldmap = new Hyperlink("Worldmap", "worldmap");
        navTable.add(logo);
        navTable.add(linkDashboard);
        navTable.add(linkCountry);
        navTable.add(linkWorldmap);


        addButton = new Button ("Add");
        deleteButton = new Button ("Delete");
        // WTF, doesnt' work
        // navTable.add(addButton);
        // navTable.add(deleteButton);
        wrapperTable.add(navTable);

        // CREATE CONTENT_WRAPPER AND ADD TO TEMPIC_WRAPPER
        contentWrapperTable = new VerticalPanel();
        contentWrapperTable.getElement().setId("content_wrapper");
        wrapperTable.add(contentWrapperTable);

    }

    public HasClickHandlers getAddButton() {
        return addButton;
    }

    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    public HasClickHandlers getList() {
        return dashboardTable;
    }

    public void setData(List<String> data) {
        dashboardTable.removeAllRows();

        for (int i = 0; i < data.size(); ++i) {
            dashboardTable.setWidget(i, 0, new CheckBox());
            dashboardTable.setText(i, 1, data.get(i));
        }
    }

    public int getClickedRow(ClickEvent event) {
        int selectedRow = -1;
        HTMLTable.Cell cell = dashboardTable.getCellForEvent(event);

        if (cell != null) {
            // Suppress clicks if the user is actually selecting the
            //  check box
            //
            if (cell.getCellIndex() > 0) {
                selectedRow = cell.getRowIndex();
            }
        }

        return selectedRow;
    }

    public List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();

        for (int i = 0; i < dashboardTable.getRowCount(); ++i) {
            CheckBox checkBox = (CheckBox)dashboardTable.getWidget(i, 0);
            if (checkBox.getValue()) {
                selectedRows.add(i);
            }
        }

        return selectedRows;
    }

    public Widget asWidget() {
        return this;
    }

}

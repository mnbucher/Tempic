package com.uzh.tempic.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;
import com.uzh.tempic.shared.TemperatureData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class WorldDashboardView extends Composite implements WorldDashboardPresenter.Display {
    // private final Button addButton;
    // private final Button deleteButton;

    private HorizontalPanel wrapperTable;
    private VerticalPanel navTable;
    private VerticalPanel contentWrapperTable;
    private VerticalPanel dashboardTable;
    private HorizontalPanel filterSection;
    private FlexTable dashboardTemperatureTable;
    private CellTable temperatureDataTable;

    public WorldDashboardView() {

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
        wrapperTable.add(navTable);

        // CREATE CONTENT_WRAPPER AND ADD TO TEMPIC_WRAPPER
        contentWrapperTable = new VerticalPanel();
        contentWrapperTable.getElement().setId("content_wrapper");

        // ADD LABEL FOR CURRENT VIEW
        Label currentViewLabel = new Label("Dashboard");
        currentViewLabel.getElement().setId("currentViewLabel");
        contentWrapperTable.add(currentViewLabel);

        // ADD DASHBOARD WRAPPER
        dashboardTable = new VerticalPanel();
        dashboardTable.getElement().setId("dashboard_wrapper");

        // ADD FILTER BAR TO DASHBOARD_WRAPPER
        filterSection = new HorizontalPanel();

        // TODO: Change to real filters
        Label filterYearStart = new Label ("filterYearStart");
        Label filterYearEnd = new Label ("filterYearEnd");
        Label filterMaxUncertainity = new Label ("filterMaxUncertainity");
        filterSection.add(filterYearStart);
        filterSection.add(filterYearEnd);
        filterSection.add(filterMaxUncertainity);
        filterSection.getElement().setId("dashboard_filterSection");
        dashboardTable.add(filterSection);

        // ADD TABLE WITH REAL TEMPERATURE DATA TO DASHBOARD_WRAPPER
        dashboardTemperatureTable = new FlexTable();
        dashboardTemperatureTable.getElement().setId("dashboard_temperatureTable");
        dashboardTable.add(dashboardTemperatureTable);

        contentWrapperTable.add(dashboardTable);

        wrapperTable.add(contentWrapperTable);

    }

    /*
    public HasClickHandlers getAddButton() {
        //return addButton;
    }

    public HasClickHandlers getDeleteButton() {
        //return deleteButton;
    }*/

    public HasClickHandlers getList() {
        return dashboardTemperatureTable;
    }

    public void setData(List<String> data) {
        dashboardTemperatureTable.removeAllRows();

        for (int i = 0; i < data.size(); ++i) {
            dashboardTemperatureTable.setWidget(i, 0, new CheckBox());
            dashboardTemperatureTable.setText(i, 1, data.get(i));
        }
    }

    public void setTemperatureTableData(ArrayList<TemperatureData> temperatureData) {

        // Create a CellTable.
        temperatureDataTable = new CellTable<TemperatureData>();

        // Create Country column.
        TextColumn<TemperatureData> countryColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getCountry();
            }
        };
        // Create City column.
        TextColumn<TemperatureData> cityColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getCity();
            }
        };

        // Add the columns.
        temperatureDataTable.addColumn(countryColumn, "Country");
        temperatureDataTable.addColumn(cityColumn, "City");

        // Push the data into the widget.
        temperatureDataTable.setRowData(0, temperatureData);

        // Add it to the root panel.
        dashboardTable.add(temperatureDataTable);
    }
    public int getClickedRow(ClickEvent event) {
        int selectedRow = -1;
        HTMLTable.Cell cell = dashboardTemperatureTable.getCellForEvent(event);

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

        for (int i = 0; i < dashboardTemperatureTable.getRowCount(); ++i) {
            CheckBox checkBox = (CheckBox)dashboardTemperatureTable.getWidget(i, 0);
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

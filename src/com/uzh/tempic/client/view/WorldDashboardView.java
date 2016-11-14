/*


    IMPORTANT: This class is still under construction (WIP).

    We don't display the section «Dashboard» in the Menu, so it isn't accessible for the user at the moment.


 */

package com.uzh.tempic.client.view;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.List;

public class WorldDashboardView extends Composite implements WorldDashboardPresenter.Display {
    // private final Button addButton;
    // private final Button deleteButton;

   // private HorizontalPanel wrapperTable;
   // private VerticalPanel navTable;
   // private VerticalPanel contentWrapperTable;
    private VerticalPanel dashboardTable;
    private HorizontalPanel filterSection;
    private FlexTable dashboardTemperatureTable;
    private CellTable temperatureDataTable;

    public WorldDashboardView() {

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("dashboard");
        initWidget(wrapperTable);

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

        wrapperTable.contentWrapperTable.add(dashboardTable);
        //wrapperTable.add(contentWrapperTable);

        // Create a CellTable.
        temperatureDataTable = new CellTable<>();

        // Set Range to something higher than 15
        temperatureDataTable.setVisibleRange(0, 500);

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

        // Create date column.
        TextColumn<TemperatureData> dateColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getDate().toString();
            }
        };
        dateColumn.setSortable(false);

        // Create avg temperature column.
        Column<TemperatureData, Number> avgTempColumn = new Column<TemperatureData, Number>(new NumberCell()) {
            @Override
            public Number getValue(TemperatureData temperatureData) {
                return temperatureData.getAvgTemperature();
            }
        };

        // Create avg temperature uncertainty column.
        Column<TemperatureData, Number> avgTempUncertaintyColumn = new Column<TemperatureData, Number>(new NumberCell()) {
            @Override
            public Number getValue(TemperatureData temperatureData) {
                return temperatureData.getAvgTemperatureUncertainty();
            }
        };

        // Create Longitude column.
        TextColumn<TemperatureData> longitudeColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getLongitude();
            }
        };

        // Create Latitude column.
        TextColumn<TemperatureData> latitudeColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getLatitude();
            }
        };

        // Add the columns.
        temperatureDataTable.addColumn(countryColumn, "Country");
        temperatureDataTable.addColumn(cityColumn, "City");
        temperatureDataTable.addColumn(avgTempColumn, "Average Temp");
        temperatureDataTable.addColumn(avgTempUncertaintyColumn, "Uncertainty");
        temperatureDataTable.addColumn(dateColumn, "Date");
        temperatureDataTable.addColumn(longitudeColumn, "Longitude");
        temperatureDataTable.addColumn(latitudeColumn, "Latitude");
        // Add it to the panel.
        dashboardTable.add(temperatureDataTable);

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
        if(temperatureData == null) { return; }
        // Create a data provider.
        ListDataProvider<TemperatureData> dataProvider = new ListDataProvider<TemperatureData>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(temperatureDataTable);

        // Push the data into the widget.
        //temperatureDataTable.setRowData(0, temperatureData);
        List<TemperatureData> tempData = dataProvider.getList();
        for (TemperatureData tData : temperatureData) {
            tempData.add(tData);
        }

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

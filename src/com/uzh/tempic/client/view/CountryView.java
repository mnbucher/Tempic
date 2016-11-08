package com.uzh.tempic.client.view;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.uzh.tempic.client.presenter.CountryPresenter;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.List;

public class CountryView extends Composite implements CountryPresenter.Display {

    private HorizontalPanel wrapperTable;
    private VerticalPanel navTable;
    private VerticalPanel contentWrapperTable;
    private VerticalPanel countryTable;
    private HorizontalPanel filterSection;
    private FlexTable dashboardTemperatureTable;
    private CellTable temperatureDataTable;
    private ListBox countryListBox;
    private ListBox fromYearListBox;
    private ListBox toYearListBox;
    private ListBox uncertaintyListBox;
    private Button filterBtn;

    public CountryView() {

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
        Label currentViewLabel = new Label("Country");
        currentViewLabel.getElement().setId("currentViewLabel");
        contentWrapperTable.add(currentViewLabel);




        // ADD Country WRAPPER
        countryTable = new VerticalPanel();
        countryTable.getElement().setId("dashboard_wrapper");

        // ADD FILTER BAR TO DASHBOARD_WRAPPER
        filterSection = new HorizontalPanel();
        filterSection.setStyleName("filterSection");

        // TODO: Implement Dropdown Changes
        countryListBox = new ListBox();
        countryListBox.setMultipleSelect(true);
        countryListBox.setStyleName("chosen-select");



        Label filterYearStart = new Label ("From:");
        fromYearListBox = new ListBox();
        for(int i = 1743; i < 2013; i++) {
            fromYearListBox.addItem(String.valueOf(i));
        }

        Label filterYearEnd = new Label ("To:");

        toYearListBox = new ListBox();
        for(int i = 1743; i < 2013; i++) {
            toYearListBox.addItem(String.valueOf(i));
        }

        Label filterMaxUncertainity = new Label ("Uncertainty:");
        uncertaintyListBox = new ListBox();
        uncertaintyListBox.addItem("< 3");
        uncertaintyListBox.addItem("< 1");


        filterBtn = new Button("Filter");

        filterSection.add(countryListBox);
        filterSection.add(filterYearStart);
        filterSection.add(fromYearListBox);
        filterSection.add(filterYearEnd);
        filterSection.add(toYearListBox);
        filterSection.add(filterMaxUncertainity);
        filterSection.add(uncertaintyListBox);
        filterSection.add(filterBtn);



        filterSection.getElement().setId("country_filterSection");
        countryTable.add(filterSection);

        // ADD TABLE WITH REAL TEMPERATURE DATA TO DASHBOARD_WRAPPER
        dashboardTemperatureTable = new FlexTable();
        dashboardTemperatureTable.getElement().setId("dashboard_temperatureTable");
        countryTable.add(dashboardTemperatureTable);

        contentWrapperTable.add(countryTable);

        wrapperTable.add(contentWrapperTable);

        // Create a CellTable.
        temperatureDataTable = new CellTable<>();

        // Set Range to something higher than 15
        temperatureDataTable.setVisibleRange(1, 500);

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
        // Create Longitude column.
        Column<TemperatureData, Number> avgTempColumn = new Column<TemperatureData, Number>(new NumberCell()) {
            @Override
            public Number getValue(TemperatureData temperatureData) {
                return temperatureData.getAvgTemperature();
            }
        };
        // Create Latitude column.
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
        temperatureDataTable.addColumn(longitudeColumn, "Longitude");
        temperatureDataTable.addColumn(latitudeColumn, "Latitude");
        // Add it to the panel.
        countryTable.add(temperatureDataTable);
    }



    public void setCountryNames(ArrayList<String> countryNames) {
        for (int i = 0; i < countryNames.size(); ++i) {
            countryListBox.addItem(countryNames.get(i));
        }
        js();
    }

    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
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

    @Override
    public void onAttach() {
        super.onAttach();
    }

    /*
     JSNI allows us to use native JS code inside JAVA
     */
    private static native void js() /*-{
        $wnd.jQuery('.chosen-select').chosen();
    }-*/;

    public Widget asWidget() {
        return this;
    }

}

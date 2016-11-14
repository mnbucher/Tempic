package com.uzh.tempic.client.view;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.uzh.tempic.client.Tempic;
import com.uzh.tempic.client.presenter.CityPresenter;
import com.uzh.tempic.shared.TemperatureData;
import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CityView extends Composite implements CityPresenter.Display{

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


    public CityView() {

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("city");
        initWidget(wrapperTable);

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
        uncertaintyListBox.addItem("< 3", "3");
        uncertaintyListBox.addItem("< 1", "1");


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

        wrapperTable.contentWrapperTable.add(countryTable);

        /*// Create a CellTable.
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
        countryColumn.setSortable(true);

        // Create City column.
        TextColumn<TemperatureData> cityColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getCity();
            }
        };
        cityColumn.setSortable(true);

        // Create avg Temperature column.
        Column<TemperatureData, Number> avgTempColumn = new Column<TemperatureData, Number>(new NumberCell()) {
            @Override
            public Number getValue(TemperatureData temperatureData) {
                return temperatureData.getAvgTemperature();
            }
        };
        avgTempColumn.setSortable(true);

        // Create uncertainity column.
        Column<TemperatureData, Number> avgTempUncertaintyColumn = new Column<TemperatureData, Number>(new NumberCell()) {
            @Override
            public Number getValue(TemperatureData temperatureData) {
                return temperatureData.getAvgTemperatureUncertainty();
            }
        };
        avgTempUncertaintyColumn.setSortable(true);

        // Create date column.
        TextColumn<TemperatureData> dateColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getDate().toString();
            }
        };
        dateColumn.setSortable(false);

        // Create Longitude column.
        TextColumn<TemperatureData> longitudeColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getLongitude();
            }
        };
        longitudeColumn.setSortable(false);

        // Create Latitude column.
        TextColumn<TemperatureData> latitudeColumn = new TextColumn<TemperatureData>() {
            @Override
            public String getValue(TemperatureData temperatureData) {
                return temperatureData.getLatitude();
            }
        };
        latitudeColumn.setSortable(false);

        // Add the columns.
        temperatureDataTable.addColumn(countryColumn, "Country");
        temperatureDataTable.addColumn(cityColumn, "City");
        temperatureDataTable.addColumn(avgTempColumn, "Average Temp");
        temperatureDataTable.addColumn(avgTempUncertaintyColumn, "Uncertainty");
        temperatureDataTable.addColumn(dateColumn, "Date");
        temperatureDataTable.addColumn(longitudeColumn, "Longitude");
        temperatureDataTable.addColumn(latitudeColumn, "Latitude");
        // Add it to the panel.
        countryTable.add(temperatureDataTable);
        */

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

package com.uzh.tempic.client.view;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.uzh.tempic.client.presenter.CountryPresenter;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.Comparator;

public class CountryView extends Composite implements CountryPresenter.Display {

    private HorizontalPanel wrapperTable;
    private VerticalPanel navTable;
    private VerticalPanel contentWrapperTable;
    private VerticalPanel countryTable;
    private HorizontalPanel filterSection;
    private HorizontalPanel multiSelectLabelSection;
    private HorizontalPanel multiSelectSection;
    private CellTable temperatureDataTable;
    private ListDataProvider<TemperatureData> dataProvider;
    private ListBox countryListBox;
    private ListBox cityListBox;
    private ListBox fromYearListBox;
    private ListBox toYearListBox;
    private ListBox uncertaintyListBox;
    private ListBox aggregateListBox;
    private ListBox groupByCityOrCountryListBox;
    private Button filterBtn;
    private SimplePager pager;

    private HorizontalPanel chart;
    private LineChartImpl LineChar;

    LayoutPanel lineChartPanel;
    LineChartImpl lineChartImpl;

    public CountryView() {

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("country");
        initWidget(wrapperTable);

        // ADD Country WRAPPER
        countryTable = new VerticalPanel();
        countryTable.getElement().setId("dashboard_wrapper");

        // ADD FILTER BAR TO DASHBOARD_WRAPPER
        filterSection = new HorizontalPanel();
        filterSection.setStyleName("filterSection");

        multiSelectSection = new HorizontalPanel();
        multiSelectSection.setStyleName("multiSelectSection");

        // Add Table for Multiselect LABELS
        multiSelectLabelSection = new HorizontalPanel();
        multiSelectLabelSection.setStyleName("multiSelectLabelSection");
        Label countryLabel = new Label("Countries");
        Label cityLabel = new Label("City");
        multiSelectLabelSection.add(countryLabel);
        multiSelectLabelSection.add(cityLabel);

        countryListBox = new ListBox();
        countryListBox.setMultipleSelect(true);
        countryListBox.setStyleName("chosen-select");

        cityListBox = new ListBox();
        cityListBox.setMultipleSelect(true);
        cityListBox.setStyleName("chosen-select");
        cityListBox.setStylePrimaryName("citybox");

        Label filterYearStart = new Label ("From:");
        fromYearListBox = new ListBox();
        for(int i = 1743; i <= 2013; i++) {
            fromYearListBox.addItem(String.valueOf(i));
        }

        Label filterYearEnd = new Label ("To:");

        toYearListBox = new ListBox();
        for(int i = 1743; i <= 2013; i++) {
            toYearListBox.addItem(String.valueOf(i));
        }

        Label filterMaxUncertainity = new Label ("Uncertainty:");
        uncertaintyListBox = new ListBox();
        uncertaintyListBox.addItem("All", "100");
        uncertaintyListBox.addItem("< 10", "10");
        uncertaintyListBox.addItem("< 5", "5");
        uncertaintyListBox.addItem("< 3", "3");

        Label filterAggregateDate = new Label ("Aggregation:");
        aggregateListBox = new ListBox();
        aggregateListBox.addItem("Month", "month");
        aggregateListBox.addItem("Year", "year");

        Label filterCityOrCountry = new Label ("Group:");
        groupByCityOrCountryListBox = new ListBox();
        groupByCityOrCountryListBox.addItem("Country", "country");
        groupByCityOrCountryListBox.addItem("City", "city");

        filterBtn = new Button("Filter");
        filterBtn.setStyleName("filter-button");

        multiSelectSection.add(countryListBox);
        multiSelectSection.add(cityListBox);

        filterSection.add(filterYearStart);
        filterSection.add(fromYearListBox);
        filterSection.add(filterYearEnd);
        filterSection.add(toYearListBox);
        filterSection.add(filterMaxUncertainity);
        filterSection.add(uncertaintyListBox);
        filterSection.add(filterAggregateDate);
        filterSection.add(aggregateListBox);
        filterSection.add(filterCityOrCountry);
        filterSection.add(groupByCityOrCountryListBox);
        filterSection.add(filterBtn);

        filterSection.getElement().setId("country_filterSection");
        countryTable.add(multiSelectLabelSection);
        countryTable.add(multiSelectSection);
        countryTable.add(filterSection);
        countryTable.setSize("100%","auto");
        lineChartImpl = new LineChartImpl();
        lineChartPanel = new LayoutPanel();
        lineChartPanel.add(lineChartImpl);
        lineChartPanel.getElement().setId("linechart-wrapper");
        lineChartPanel.setSize("100%", "350px");
        countryTable.add(lineChartPanel);


        // The list data provider allows us to change the underlying list and the table will automatically be updated.
        temperatureDataTable = new CellTable<>();
        temperatureDataTable.getElement().setId("temperatureTable");
        dataProvider = new ListDataProvider<TemperatureData>();
        dataProvider.addDataDisplay(temperatureDataTable);

        // How many items should be display on one table page
        temperatureDataTable.setVisibleRange(0, 50);


        // Create a Pager to control the table.
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
        pager.setDisplay(temperatureDataTable);


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
        dateColumn.setSortable(true);


        // Add the columns.
        temperatureDataTable.addColumn(countryColumn, "Country");
        temperatureDataTable.addColumn(cityColumn, "City");
        temperatureDataTable.addColumn(avgTempColumn, "Average Temp");
        temperatureDataTable.addColumn(avgTempUncertaintyColumn, "Uncertainty");
        temperatureDataTable.addColumn(dateColumn, "Date");

        // Add it to the panel.
        countryTable.add(temperatureDataTable);
        countryTable.add(pager);

        wrapperTable.contentWrapperTable.add(countryTable);


    }

    public HasClickHandlers getFilterButton() {
        return filterBtn;
    }

    public ListBox getCountryListBox() {
        return countryListBox;
    }
    public ListBox getCityListBox() {
        return cityListBox;
    }
    public ListBox getFromYearListBox() {
        return fromYearListBox;
    }
    public ListBox getToYearListBox() {
        return toYearListBox;
    }
    public ListBox getUncertaintyListBox() {
        return uncertaintyListBox;
    }
    public ListBox getAggregateListBox() {
        return aggregateListBox;
    }
    public ListBox getGroupByCityOrCountryListBox() {
        return groupByCityOrCountryListBox;
    }
    public void setCountryNames(ArrayList<String> countryNames) {
        for(String countryName : countryNames) {
            countryListBox.addItem(countryName);
        }
        js();
    }
    public void setCityNames(ArrayList<String> cityNames) {
        for(String cityName : cityNames) {
            cityListBox.addItem(cityName);
        }
        js2();
    }
    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
        if(temperatureData.size() == 0) { return; } // Don't update view if there's no data
        lineChartImpl.setTemperatureData(temperatureData);
        // Fill our dataProvider with the data from the backend
        dataProvider.setList(temperatureData);

        ColumnSortEvent.ListHandler<TemperatureData> countryColumnSortHandler = new ColumnSortEvent.ListHandler<TemperatureData>(dataProvider.getList());
        countryColumnSortHandler.setComparator(temperatureDataTable.getColumn(0), new Comparator<TemperatureData>(){
            public int compare(TemperatureData a, TemperatureData b){
                if (a == b){
                    return 0;
                }
                if (a != null){
                    return (b != null) ? a.getCountry().compareTo(b.getCountry()) : 1;
                }
                return -1;
            }
        });
        // Add the Sorthandler to the Table
        temperatureDataTable.addColumnSortHandler(countryColumnSortHandler);

        // We know that the data is sorted alphabetically by country default.
        temperatureDataTable.getColumnSortList().push(temperatureDataTable.getColumn(0));


        // City ColumnSortEvent.ListHandler
        ColumnSortEvent.ListHandler<TemperatureData> cityColumnSortHandler = new ColumnSortEvent.ListHandler<TemperatureData>(dataProvider.getList());
        cityColumnSortHandler.setComparator(temperatureDataTable.getColumn(1), new Comparator<TemperatureData>(){
            public int compare(TemperatureData a, TemperatureData b){
                if (a == b){
                    return 0;
                }
                if (a != null){
                    return (b != null) ? a.getCity().compareTo(b.getCity()) : 1;
                }
                return -1;
            }
        });

        temperatureDataTable.addColumnSortHandler(cityColumnSortHandler);

        // AvgTemperature ColumnSortEvent.ListHandler
        ColumnSortEvent.ListHandler<TemperatureData> avgTempColumnSortHandler = new ColumnSortEvent.ListHandler<TemperatureData>(dataProvider.getList());
        avgTempColumnSortHandler.setComparator(temperatureDataTable.getColumn(2), new Comparator<TemperatureData>(){
            public int compare(TemperatureData a, TemperatureData b){
                if (a == b){
                    return 0;
                }
                if (a != null){
                    return (b != null) ? a.getAvgTemperature().compareTo(b.getAvgTemperature()) : 1;
                }
                return -1;
            }
        });
        temperatureDataTable.addColumnSortHandler(avgTempColumnSortHandler);


        // MeasurementError ColumnSortEvent.ListHandler
        ColumnSortEvent.ListHandler<TemperatureData> measurementErrColumnSortHandler = new ColumnSortEvent.ListHandler<TemperatureData>(dataProvider.getList());
        measurementErrColumnSortHandler.setComparator(temperatureDataTable.getColumn(3), new Comparator<TemperatureData>(){
            public int compare(TemperatureData a, TemperatureData b){
                if (a == b){
                    return 0;
                }
                if (a != null){
                    return (b != null) ? a.getAvgTemperatureUncertainty().compareTo(b.getAvgTemperatureUncertainty()) : 1;
                }
                return -1;
            }
        });
        temperatureDataTable.addColumnSortHandler(measurementErrColumnSortHandler);

        // Date ColumnSortEvent.ListHandler
        ColumnSortEvent.ListHandler<TemperatureData> dateColumnSortHandler = new ColumnSortEvent.ListHandler<TemperatureData>(dataProvider.getList());
        dateColumnSortHandler.setComparator(temperatureDataTable.getColumn(4), new Comparator<TemperatureData>(){
            public int compare(TemperatureData a, TemperatureData b){
                if (a == b){
                    return 0;
                }
                if (a != null){
                    return (b != null) ? a.getDate().compareTo(b.getDate()) : 1;
                }
                return -1;
            }
        });
        temperatureDataTable.addColumnSortHandler(dateColumnSortHandler);



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
        $wnd.jQuery("#print-link" ).click(function() {
            $wnd.print();
        });
    }-*/;

    private static native void js2() /*-{
        $wnd.jQuery('.citybox').chosen();
    }-*/;

    public Widget asWidget() {
        return this;
    }

}

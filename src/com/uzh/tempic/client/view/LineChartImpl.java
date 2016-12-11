package com.uzh.tempic.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LineChartImpl extends DockLayoutPanel {
    LineChart chart;

    public LineChartImpl() {
        super(Unit.PX);
        initialize();
    }

    private void initialize() {
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                // Create and attach the chart
                chart = new LineChart();
                add(chart);
            }
        });
    }

    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {

        Set<Date> datesSet = new HashSet<>();  // stores the date columns
        Set<String> citiesSet = new HashSet<>(); // stores the city columns


        for(int i = 0; i < temperatureData.size(); i++) {
            datesSet.add(temperatureData.get(i).getDate());
            if(temperatureData.get(i).getCity().equals("")) {
                citiesSet.add(temperatureData.get(i).getCountry());
            } else {
                citiesSet.add(temperatureData.get(i).getCity());
            }
        }

        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.DATE, "Date"); // Adds first date column


        ArrayList<Date> sortedDates = new ArrayList(datesSet);
        Collections.sort(sortedDates);

        ArrayList<String> sortedCities = new ArrayList(citiesSet);
        Collections.sort(sortedCities);


        for (String sortedCity : sortedCities) { // adds a column for every country
            dataTable.addColumn(ColumnType.NUMBER, sortedCity);
        }

        dataTable.addRows(datesSet.size()); // sets amount of rows

        for (int i = 0; i < sortedDates.size(); i++) { // add a row for each date
            dataTable.setValue(i, 0, sortedDates.get(i));
        }

        for (TemperatureData tempData : temperatureData) {
            int row = sortedDates.indexOf(tempData.getDate());
            int col;
            if (tempData.getCity().equals("")) {
                col = sortedCities.indexOf(tempData.getCountry()) + 1;
            } else {
                col = sortedCities.indexOf(tempData.getCity()) + 1;
            }

            dataTable.setValue(row, col, tempData.getAvgTemperature());
        }


        // Set options
        LineChartOptions options = LineChartOptions.create();
        options.setBackgroundColor("#f0f0f0");
        options.setTitle("Average temperature over time");
        options.setHAxis(HAxis.create("Date"));
        options.setVAxis(VAxis.create("Temperature"));
        options.setInterpolateNulls(false);


        // Draw the chart
        chart.draw(dataTable, options);
    }
}

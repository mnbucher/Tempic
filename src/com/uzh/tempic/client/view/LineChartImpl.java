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

import java.sql.Array;
import java.util.ArrayList;
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
        Set<String> countries = new HashSet<>();
        ArrayList<Integer> years = new ArrayList<>();
        for (TemperatureData aTemperatureData : temperatureData) {
            countries.add(aTemperatureData.getCountry());
        }
        ArrayList<ArrayList<Double>> allCountryData = new ArrayList<>();
        for (String country : countries) {
            ArrayList<Double> countryData = new ArrayList<>();
            for (TemperatureData aTemperatureData : temperatureData) {
                if(aTemperatureData.getCountry().equals(country)) {
                    countryData.add(aTemperatureData.getAvgTemperature());
                }
            }
            allCountryData.add(countryData);
        }
        //String[] countries = new String[]{"Austria"};
        //int[] years = new int[]{2003, 2004, 2005, 2006, 2007, 2008};
        int[][] values = new int[][]{{1336060, 1538156, 1576579, 1600652, 1968113, 1901067}};


        // Prepare the data
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Year");
        for (String country : countries) {
            dataTable.addColumn(ColumnType.NUMBER, country);
        }
        dataTable.addRows(years.size());
        for (int i = 0; i < years.size(); i++) {
            dataTable.setValue(i, 0, String.valueOf(years.get(i)));
        }
        for (int col = 0; col < values.length; col++) {
            for (int row = 0; row < values[col].length; row++) {
                dataTable.setValue(row, col + 1, values[col][row]);
            }
        }

        // Set options
        LineChartOptions options = LineChartOptions.create();
        options.setBackgroundColor("#f0f0f0");
        //options.setFontName("Tahoma");
        options.setTitle("Yearly Coffee Consumption by Country");
        options.setHAxis(HAxis.create("Year"));
        options.setVAxis(VAxis.create("Cups"));


        // Draw the chart
        chart.draw(dataTable, options);
    }
}

package com.uzh.tempic.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.format.DateFormat;
import com.googlecode.gwt.charts.client.options.AggregationTarget;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Trendline;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.uzh.tempic.shared.TemperatureData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

        Map<String, ArrayList<TemperatureData>> citiesMap = new HashMap<>();
        Set<Date> datesSet = new HashSet<>();

        for(int i = 0; i < temperatureData.size(); i++) {
            citiesMap.putIfAbsent(temperatureData.get(i).getCity(), new ArrayList<TemperatureData>());
            citiesMap.get(temperatureData.get(i).getCity()).add(temperatureData.get(i));
            datesSet.add(temperatureData.get(i).getDate());
        }

        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.DATE, "Date");

        for (String key : citiesMap.keySet()) {
            dataTable.addColumn(ColumnType.NUMBER, key);
        }
        dataTable.addRows(datesSet.size());

        List<Date> sortedDates = new ArrayList(datesSet);
        Collections.sort(sortedDates);

        for (int i = 0; i < sortedDates.size(); i++) {
            dataTable.setValue(i, 0, sortedDates.get(i));
        }
        int col = 1;
        for (ArrayList<TemperatureData> tempList : citiesMap.values()) {
            for(int i = 0; i < tempList.size(); i++) {
                int row = sortedDates.indexOf(tempList.get(i).getDate());
                dataTable.setValue(row,col,tempList.get(i).getAvgTemperature());
            }
            col++;
        }

        /*ArrayList<ArrayList<Double>> allCountryData = new ArrayList<>();
        for (String country : countries) {
            ArrayList<Double> countryData = new ArrayList<>();
            for (TemperatureData aTemperatureData : temperatureData) {
                if(aTemperatureData.getCountry().equals(country)) {
                    countryData.add(aTemperatureData.getAvgTemperature());
                }
            }
            allCountryData.add(countryData);
        }*/
        //String[] countries = new String[]{"Austria"};
        //int[] years = new int[]{2003, 2004, 2005, 2006, 2007, 2008};
        int[][] values = new int[][]{{1336060, 1538156, 1576579, 1600652, 1968113, 1901067}};
        /*ArrayList<Date> dates = new ArrayList<>();
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());*/

        // Prepare the data
        //DataTable dataTable = ChartHelper.arrayToDataTable(data);
        //dataTable.addColumn(ColumnType.DATE, "Date");
        /*
        for (String country : countries) {
            dataTable.addColumn(ColumnType.NUMBER, country);
        }
        dataTable.addRows(dateSet.size());
        for (int i = 0; i < dateSet.size(); i++) {
            dataTable.setValue(i, 0, dateSet.toArray()[i]);
        }
        for (int col = 0; col < values.length; col++) {
            for (int row = 0; row < values[col].length; row++) {
                dataTable.setValue(row, col + 1, values[col][row]);
            }
        }*/

        // Set options
        LineChartOptions options = LineChartOptions.create();
        options.setBackgroundColor("#f0f0f0");
        //options.setFontName("Tahoma");
        options.setTitle("Average temperature over time");
        options.setHAxis(HAxis.create("Date"));
        options.setVAxis(VAxis.create("Temperature"));
        options.setInterpolateNulls(false);


        // Draw the chart
        chart.draw(dataTable, options);
    }
}

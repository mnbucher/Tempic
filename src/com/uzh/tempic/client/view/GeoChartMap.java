package com.uzh.tempic.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.map.Map;
import com.googlecode.gwt.charts.client.map.MapOptions;
import com.googlecode.gwt.charts.client.options.DisplayMode;
import com.googlecode.gwt.charts.client.options.MapType;
import com.googlecode.gwt.charts.client.util.ChartHelper;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public class GeoChartMap extends DockLayoutPanel {

    private GeoChart geoChart;

    public GeoChartMap() {
        super(Unit.PX);
        initialize();
    }

    private void initialize() {
        ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                // Create and attach the chart
                geoChart = new GeoChart();
                add(geoChart);
            }
        });
    }


    /** Updates the map with the specified temperatureData
     * @pre temperatureData ArrayList must contain only one entry per city
     * @post map is updated with the provided values
     * @param temperatureData an ArrayList with the temperatureData objects that should be displayed
     *
     */
    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
        Object[][] data = new Object[temperatureData.size() + 1][4];
        data[0] = new Object[]{"Lat","Long", "Temperature", "Uncertainty"};

        for(int i = 1; i < temperatureData.size(); i++) {
            data[i][0] = temperatureData.get(i).getDecimalLatitude();
            data[i][1] = temperatureData.get(i).getDecimalLongitude();
            data[i][2] = temperatureData.get(i).getAvgTemperature();
            data[i][3] = temperatureData.get(i).getAvgTemperatureUncertainty();
        }

        DataTable dataTable = ChartHelper.arrayToDataTable(data);
        GeoChartOptions options = GeoChartOptions.create();
        options.setDisplayMode(DisplayMode.MARKERS);
        GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
        geoChartColorAxis.setColors("green", "yellow", "red");
        options.setColorAxis(geoChartColorAxis);
        geoChart.draw(dataTable,options);
    }
}



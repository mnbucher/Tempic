package com.uzh.tempic.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.map.Map;
import com.googlecode.gwt.charts.client.map.MapOptions;
import com.googlecode.gwt.charts.client.options.MapType;
import com.googlecode.gwt.charts.client.util.ChartHelper;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public class GoogleMap extends DockLayoutPanel {

    private Map chart;

    public GoogleMap() {
        super(Unit.PX);
        initialize();
    }

    private void initialize() {
        ChartLoader chartLoader = new ChartLoader(ChartPackage.MAP);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                // Create and attach the chart
                chart = new Map();
                add(chart);
            }
        });
    }

    /** Converts temperature values into hex colorcodes
     * @pre: temperature should be between -20 and 40
     * @post: retrieve a sixdigit hexcode as string
     * @param temperature a double with the temperature that should be converted
     * @return HexString with the color value for the specified temperature
     */
    public String temperatureToHexValue(double temperature){
         String hex;
         int R;
         int B;
         int G = 0;
         temperature = temperature +20;
         if (temperature <= 30){
             R = (int) Math.round(temperature * 225 /30);
             B = 225;
         }
         else {
             R = 225;
             B = (int) Math.round(225 - (temperature * 225 /30));
         }
         hex = Integer.toHexString(R).concat("00").concat(Integer.toHexString(B));

    return hex;
    }

    /** Updates the map with the specified temperatureData
     * @pre temperatureData ArrayList must contain only one entry per city
     * @post map is updated with the provided values
     * @param temperatureData an ArrayList with the temperatureData objects that should be displayed
     *
     */
    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
        Object[][] data = new Object[temperatureData.size() + 1][4];
        data[0] = new Object[]{"Lat","Long", "Temperature","Icon"};

        for(int i = 1; i < temperatureData.size(); i++) {
            data[i][0] = temperatureData.get(i).getDecimalLatitude();
            data[i][1] = temperatureData.get(i).getDecimalLongitude();
            data[i][2] = temperatureData.get(i).getCity() + ": \n" + temperatureData.get(i).getAvgTemperature().toString() + " ° C";
        }

        DataTable dataTable = ChartHelper.arrayToDataTable(data);
        MapOptions options = MapOptions.create();
        options.setShowTip(true);
        options.setUseMapTypeControl(true);
        options.setZoomLevel(3);
        options.setMapType(MapType.HYBRID);
        chart.draw(dataTable,options);
    }
}


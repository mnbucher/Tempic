package com.uzh.tempic.client.view;

// TODO: Check if any libraries are not used

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldMapPresenter;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.List;

public class WorldMapView extends Composite implements WorldMapPresenter.Display {

    private VerticalPanel dashboardTable;
    private final Button yearSlider;
    private GoogleMap googleMap;
    private GeoChartMap geoChart;

    public WorldMapView(){

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("worldmap");
        initWidget(wrapperTable);


        // Create GoogleMap Class and add it to the panel
        googleMap = new GoogleMap();
        googleMap.getElement().setId("googlemap");
        wrapperTable.contentWrapperTable.add(googleMap);

        /*
        geoChart = new GeoChartMap();
        geoChart.getElement().setId("googlemap");
        wrapperTable.contentWrapperTable.add(geoChart);*/

        // Create & add custom slider to the view
        yearSlider =  new Button("Slider for years");

    }

    public GoogleMap getGoogleMap() { return googleMap; }
    public HasClickHandlers getYearSlider() {
        return yearSlider;
    }

    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
        if (temperatureData == null) {
            return;
        }
        //geoChart.setTemperatureData(temperatureData);
        googleMap.setTemperatureData(temperatureData);
    }

    public Widget asWidget() {
        return this;
    }

}

package com.uzh.tempic.client.view;

// TODO: Check if any libraries are not used

import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldMapPresenter;
import com.uzh.tempic.client.widget.slider.Slider;
import com.uzh.tempic.client.widget.slider.SliderListener;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public class WorldMapView extends Composite implements WorldMapPresenter.Display {

    private VerticalPanel dashboardTable;
    private GoogleMap googleMap;
    private GeoChartMap geoChart;
    private Slider yearSlider;
    private Label yearSliderLabel;
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

        FlowPanel sliderWrapper = new FlowPanel();
        sliderWrapper.getElement().setId("sliderwrapper");
        Label sliderLabel = new Label("Year:");
        yearSliderLabel = new Label("2012");
        yearSliderLabel.addStyleName("slider-values");
        yearSlider = new Slider("slider",1743,2013,2012);
        sliderWrapper.add(sliderLabel);
        sliderWrapper.add(yearSliderLabel);
        sliderWrapper.add(yearSlider);

        wrapperTable.contentWrapperTable.add(sliderWrapper);

    }

    public GoogleMap getGoogleMap() { return googleMap; }

    public Slider getYearSlider() { return yearSlider; }
    public Label getYearSliderLabel() { return yearSliderLabel; }


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

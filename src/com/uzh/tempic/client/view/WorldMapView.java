package com.uzh.tempic.client.view;

// TODO: Check if any libraries are not used

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.presenter.WorldMapPresenter;
import com.uzh.tempic.client.widget.slider.Slider;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public class WorldMapView extends Composite implements WorldMapPresenter.Display {

    private VerticalPanel dashboardTable;
    private GoogleMapV3 googleMap;
    private GeoChartMap geoChart;
    private Slider yearSlider;
    public WorldMapView(){

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("worldmap");
        initWidget(wrapperTable);


        // Create GoogleMap Class and add it to the panel
        googleMap = new GoogleMapV3();
        googleMap.getElement().setId("googlemap");
        wrapperTable.contentWrapperTable.add(googleMap);

        /*
        geoChart = new GeoChartMap();
        geoChart.getElement().setId("googlemap");
        wrapperTable.contentWrapperTable.add(geoChart);*/

        // Create & add custom slider to the view

        FlowPanel sliderWrapper = new FlowPanel();
        sliderWrapper.getElement().setId("sliderwrapper");
        yearSlider = new Slider("slider",1743,2013,2012);

         yearSlider.addAttachHandler(new AttachEvent.Handler() {

            @Override
            public void onAttachOrDetach(AttachEvent event) {
                yearSlider.getElement().getFirstChildElement().setInnerText("2012");
            }
        });

        sliderWrapper.add(yearSlider);
        wrapperTable.contentWrapperTable.add(sliderWrapper);


    }



    public GoogleMapV3 getGoogleMap() { return googleMap; }

    public Slider getYearSlider() { return yearSlider; }

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

package com.uzh.tempic.client.view;

// TODO: Check if any libraries are not used

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldMapPresenter;

import java.util.ArrayList;
import java.util.List;

public class WorldMapView extends Composite implements WorldMapPresenter.Display {

    private VerticalPanel dashboardTable;
    private final Button yearSlider;
    private GoogleMap googleMap;

    public WorldMapView(){

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("worldmap");
        initWidget(wrapperTable);

        // Create GoogleMap Class and add it to the panel
        googleMap = new GoogleMap();
        googleMap.getElement().setId("googlemap");
        wrapperTable.contentWrapperTable.add(googleMap);

        // Create & add custom slider to the view
        yearSlider =  new Button("Slider for years");

    }

    public HasClickHandlers getYearSlider() {
        return yearSlider;
    }

    public Widget asWidget() {
        return this;
    }

}

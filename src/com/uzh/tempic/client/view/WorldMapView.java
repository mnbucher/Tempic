package com.uzh.tempic.client.view;

// TODO: Check if any libraries are not used

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldMapPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 2.11.16.
 */
public class WorldMapView extends Composite implements WorldMapPresenter.Display {

    private final Button yearSlider = new Button("Slider for years");
    private final FlexTable contentTable;

    public WorldMapView(){

        // CREATE NAV AND APPLY LAYOUT
        WrapperTable wrapperTable = new WrapperTable("worldmap");
        initWidget(wrapperTable);

        contentTable = new FlexTable();
        contentTable.setWidth("100%");
        contentTable.getCellFormatter().addStyleName(0, 0, "worldDashboard-ListContainer");
        contentTable.getCellFormatter().setWidth(0, 0, "100%");
        contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);

        // Create the menu
        //
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setBorderWidth(0);
        hPanel.setSpacing(0);
        hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        contentTable.getCellFormatter().addStyleName(0, 0, "worldDashboard-ListMenu");
        contentTable.setWidget(0, 0, hPanel);

        wrapperTable.contentWrapperTable.add(contentTable);

    }

    public HasClickHandlers getYearSlider() {
        return yearSlider;
    }

    public Widget asWidget() {
        return this;
    }

}

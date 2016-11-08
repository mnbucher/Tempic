package com.uzh.tempic.client.view;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WrapperTable extends HorizontalPanel {

    private VerticalPanel navTable;
    protected VerticalPanel contentWrapperTable;

    public WrapperTable(String currentView){

        super.setWidth("100%");
        super.getElement().setId("tempic_wrapper");

        // CREATE NAV AND ADD TO TEMPIC_WRAPPER
        navTable = new VerticalPanel();
        navTable.getElement().setId("nav");
        Label logo = new Label("Tempic");
        logo.getElement().setId("logo");

        // Placeholder for Layout (Tables FTW)
        Label placeholder = new Label("");
        placeholder.getElement().setId("navi_placeholder");

        Hyperlink linkDashboard = new Hyperlink("Dashboard", "dashboard");
        Hyperlink linkWorldmap = new Hyperlink("Worldmap", "worldmap");
        Hyperlink linkCountry = new Hyperlink("Country", "country");
        Hyperlink linkCity = new Hyperlink("City", "city");

        if(currentView == "dashboard") { linkDashboard.getElement().setId("active"); }
        else if(currentView == "worldmap") { linkWorldmap.getElement().setId("active"); }
        else if(currentView == "country") { linkCountry.getElement().setId("active"); }
        else if(currentView == "city") { linkCity.getElement().setId("active"); }

        navTable.add(logo);
        navTable.add(placeholder);
        navTable.add(linkDashboard);
        navTable.add(linkWorldmap);
        navTable.add(linkCountry);
        navTable.add(linkCity);
        super.add(navTable);

        // CREATE CONTENT_WRAPPER AND ADD TO TEMPIC_WRAPPER
        contentWrapperTable = new VerticalPanel();
        contentWrapperTable.getElement().setId("content_wrapper");

        // ADD LABEL FOR CURRENT VIEW

        Label currentViewLabel;

        if(currentView == "dashboard") { currentViewLabel = new Label("Dashboard"); }
        else if(currentView == "worldmap") { currentViewLabel = new Label("Worldmap"); }
        else if(currentView == "country") { currentViewLabel = new Label("Country"); }
        else { currentViewLabel = new Label("City"); }

        currentViewLabel.getElement().setId("currentViewLabel");
        contentWrapperTable.add(currentViewLabel);

        super.add(contentWrapperTable);

    }

}

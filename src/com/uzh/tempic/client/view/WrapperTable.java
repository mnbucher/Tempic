package com.uzh.tempic.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;

public class WrapperTable extends HorizontalPanel {

    private VerticalPanel navTable;
    protected VerticalPanel contentWrapperTable;

    public WrapperTable(String currentView){

        super.setWidth("100%");
        super.getElement().setId("tempic_wrapper");

        // CREATE NAV AND ADD TO TEMPIC_WRAPPER
        navTable = new VerticalPanel();
        navTable.getElement().setId("nav");

        // Set Logo
        Label logo = new Label("tempic");
        logo.getElement().setId("logo");

        // Placeholder for Layout (Tables FTW)
        Label placeholder = new Label("");
        placeholder.getElement().setId("navi_placeholder");

        Hyperlink linkWorldmap = new Hyperlink("Explore", "worldmap");
        linkWorldmap.addStyleName("worldmap_link");

        Hyperlink linkCountry = new Hyperlink("Analyze", "country");
        linkCountry.addStyleName("country_link");

        //if(currentView == "dashboard") { linkDashboard.getElement().setId("active"); }
        if(currentView == "worldmap") { linkWorldmap.getElement().setId("active"); }
        else if(currentView == "country") { linkCountry.getElement().setId("active"); }

        // Copyright
        HTML copyright = new HTML("<p class='copyright_uzh'>© 2016 Department of Informatics, University of Zurich. All rights reserved.</p><p class='datasource'>Source of raw data: <a href='http://www.berkeleyearth.org' target='_blank'>berkeleyearth.org</a></p>");
        copyright.getElement().setId("copyright");

        navTable.add(logo);
        navTable.add(placeholder);
        navTable.add(linkWorldmap);
        navTable.add(linkCountry);
        navTable.add(copyright);

        super.add(navTable);

        // CREATE CONTENT_WRAPPER AND ADD TO TEMPIC_WRAPPER
        contentWrapperTable = new VerticalPanel();
        contentWrapperTable.getElement().setId("content_wrapper");

        // ADD LABEL FOR CURRENT VIEW

        Label currentViewLabel;

        //if(currentView == "dashboard") { currentViewLabel = new Label("Dashboard"); }
        if(currentView == "worldmap") { currentViewLabel = new Label("Worldmap"); }
        else if(currentView == "country") { currentViewLabel = new Label("Country"); }
        else { currentViewLabel = new Label("City"); }

        currentViewLabel.getElement().setId("currentViewLabel");
        contentWrapperTable.add(currentViewLabel);

        super.add(contentWrapperTable);

    }

}

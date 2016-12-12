package com.uzh.tempic.client.view;

import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.MouseEvent;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.Circle;
import com.google.gwt.maps.client.overlays.CircleOptions;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.uzh.tempic.shared.TemperatureDataComparison;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by michaelziorjen on 26.11.16.
 */
public class GoogleMap extends Composite {

    private VerticalPanel pWidget;
    private MapWidget mapWidget;
    private InfoWindow infoWindow;

    private ArrayList<Circle> markerList = new ArrayList<>();

    public GoogleMap() {
        pWidget = new VerticalPanel();
        initWidget(pWidget);

        loadMapApi();
    }

    private void draw() {
        pWidget.clear();
        LatLng center = LatLng.newInstance(49.496675, -102.65625);
        MapOptions opts = MapOptions.newInstance();
        opts.setZoom(3);
        opts.setCenter(center);
        opts.setMapTypeId(MapTypeId.HYBRID);

        mapWidget = new MapWidget(opts);
        pWidget.add(mapWidget);
        mapWidget.setSize("100%", 	(Window.getClientHeight() - 72) + "px" );

        InfoWindowOptions iwOptions = InfoWindowOptions.newInstance();
        infoWindow = InfoWindow.newInstance(iwOptions);
    }

    private void loadMapApi() {
        boolean sensor = true;

        // load all the libs for use in the maps
        ArrayList<LoadApi.LoadLibrary> loadLibraries = new ArrayList<>();
        loadLibraries.add(LoadApi.LoadLibrary.DRAWING);
        loadLibraries.add(LoadApi.LoadLibrary.GEOMETRY);
        loadLibraries.add(LoadApi.LoadLibrary.VISUALIZATION);

        Runnable onLoad = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        String otherParams = "key=AIzaSyD-JL6QlH5Z5isI2Tur1KEfBYjcAALnh_E";

        LoadApi.go(onLoad, loadLibraries, sensor, otherParams);
    }

    private void drawMarker(Double lat, Double lng, HTML infoWindowHTML, Double tempDiff) {
        LatLng pos = LatLng.newInstance(lat, lng);
        CircleOptions circleOpts = CircleOptions.newInstance();
        circleOpts.setCenter(pos);
        String color = this.getColor(tempDiff);
        circleOpts.setFillColor(color);
        circleOpts.setFillOpacity(0.9d);
        circleOpts.setRadius(100000);
        circleOpts.setStrokeWeight(0);
        circleOpts.setMap(mapWidget);

        final Circle circle = Circle.newInstance(circleOpts);
        circle.setMap(mapWidget);
        markerList.add(circle);

        final HTML innerHTML = infoWindowHTML;

        circle.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                drawInfoWindow(circle, event.getMouseEvent(),innerHTML);
            }
        });
    }

    protected void drawInfoWindow(final Circle circle, MouseEvent mouseEvent, HTML infoWindowHTML) {
        if (circle == null || mouseEvent == null) {
            return;
        }

        SimplePanel vp = new SimplePanel();
        vp.add(infoWindowHTML);

        infoWindow.setContent(vp);
        infoWindow.setPosition(circle.getCenter());
        infoWindow.open(mapWidget);


    }

    /** Updates the map with the specified temperatureData
     * @pre temperatureData ArrayList must contain only one entry per city
     * @post map is updated with the provided values
     * @param temperatureData an ArrayList with the temperatureData objects that should be displayed
     *
     */
    public void setTemperatureData(ArrayList<TemperatureDataComparison> temperatureData) {
        deleteMapFromAllMarkers();

        for (TemperatureDataComparison tempComp : temperatureData) {
            String infoWindow = "<strong>" + tempComp.getCity() + "</strong> <br/> " +
                    "<strong>Temp. Difference: </strong>" + tempComp.getFormattedTemperatureDifferencePercent() + "<br />" +
                    "<strong>Absolute Temp. Difference: </strong>" + tempComp.getTemperatureDifference() + "<br />" +
                    "<strong>Start Year: </strong>" + tempComp.getYearA() + ", " + tempComp.getAvgTemperatureA() + " °C</br>" +
                    "<strong>End Year: </strong>" + tempComp.getYearB() + ", " + tempComp.getAvgTemperatureB() + " °C</br>" +
                    "<strong>Timespan: </strong>" + tempComp.getYearDifference() + " years";

            HTML infoWindowHTML = new HTML(infoWindow);

            drawMarker(tempComp.getDecimalLatitude(), tempComp.getDecimalLongitude(), infoWindowHTML, tempComp.getTemperatureDifference());
        }
    }

    /**
     * Removes all markers from the map
     */
    private void deleteMapFromAllMarkers() {
        for(int i=0; i < markerList.size(); i++) {
            this.markerList.get(i).setMap(null);
        }
        markerList.clear();
    }

    /** To convert temperature values into hex colorcodes
     * @pre: temperature should be between -20 and 40
     * @param:
     * @return: String : retrieve a sixdigit hexcode
     * **/


    public String getColor(double power)
    {
        //normalize power to a range [0,1]
        power = power + 10;
        power = power/20;
        //init values
        double blue = 0.0;
        double red = 0.0;
        double green = 0.0;
        if (power<0.5) {
            green = 1.0;
            red = 2 * power;
        }
        else if (0.5<=power) {
            red = 1.0;
            green = 1.0 - 2 * (power - 0.5);
        }
        red = red * 255;
        green = green * 255;
        int r = (int)red;
        int g = (int)green;
        int b = (int)blue;
        String hex = toHex(r, g, b);
        return hex;
    }

    /**
     * Returns a web browser-friendly HEX value representing the colour in the default sRGB
     * ColorModel.
     *
     * @param r red
     * @param g green
     * @param b blue
     * @return a browser-friendly HEX value
     */
    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }



}

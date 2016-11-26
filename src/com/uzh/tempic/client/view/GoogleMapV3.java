package com.uzh.tempic.client.view;

import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.MouseEvent;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.options.MapType;
import com.googlecode.gwt.charts.client.util.ChartHelper;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

/**
 * Created by michaelziorjen on 26.11.16.
 */
public class GoogleMapV3 extends Composite {

    private VerticalPanel pWidget;
    private MapWidget mapWidget;
    private InfoWindow infoWindow;

    private ArrayList<Marker> markerList = new ArrayList<>();

    public GoogleMapV3() {
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
        mapWidget.setSize("700px", "100px");

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

        LoadApi.go(onLoad, loadLibraries, sensor);
    }

    private void drawMarker(Double lat, Double lng, HTML infoWindowHTML) {
        LatLng pos = LatLng.newInstance(lat, lng);
        MarkerOptions options = MarkerOptions.newInstance();
        options.setPosition(pos);

        final Marker marker = Marker.newInstance(options);
        marker.setMap(mapWidget);
        markerList.add(marker);

        final HTML innerHTML = infoWindowHTML;

        marker.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                drawInfoWindow(marker, event.getMouseEvent(),innerHTML);
            }
        });
    }

    protected void drawInfoWindow(final Marker marker, MouseEvent mouseEvent, HTML infoWindowHTML) {
        if (marker == null || mouseEvent == null) {
            return;
        }

        SimplePanel vp = new SimplePanel();
        vp.add(infoWindowHTML);

        infoWindow.setContent(vp);

        infoWindow.open(mapWidget, marker);


    }

    /** Updates the map with the specified temperatureData
     * @pre temperatureData ArrayList must contain only one entry per city
     * @post map is updated with the provided values
     * @param temperatureData an ArrayList with the temperatureData objects that should be displayed
     *
     */
    public void setTemperatureData(ArrayList<TemperatureData> temperatureData) {
        deleteMapFromAllMarkers();

        for(int i = 0; i < temperatureData.size(); i++) {
            HTML infoWindow = new HTML("<strong>" + temperatureData.get(i).getCity() + "</strong> <br/> Temperature Difference: " + temperatureData.get(i).getAvgTemperature().toString() + " ° C" );
            drawMarker(temperatureData.get(i).getDecimalLatitude(),temperatureData.get(i).getDecimalLongitude(),infoWindow);
        }
    }

    /**
     * Removes all markers from the map
     */
    private void deleteMapFromAllMarkers() {
        for(int i=0; i < markerList.size(); i++) {
            this.markerList.get(i).clear();
        }
        markerList.clear();
    }


}

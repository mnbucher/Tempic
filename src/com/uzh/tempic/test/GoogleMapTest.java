package com.uzh.tempic.test;

import com.uzh.tempic.client.view.GoogleMap;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TemperatureDataComparison;
import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.ArrayList;
import java.util.Date;


public class GoogleMapTest extends GWTTestCase{

    /**
     * Specifies a module to use when running this test case. The returned
     * module must include the source for this class.
     *
     * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
     */
    @Override
    public String getModuleName() {
        return "com.uzh.tempic.tempic";
    }

    /**
     * Tests the GoogleMaps.getColor function
     */
    @Test
    public void testGetColor() {
        GoogleMap googlemap = new GoogleMap();
        double minimum = -10;
        double maximum = 10;
        assertEquals("#00FF00", googlemap.getColor(minimum));
        assertEquals("#FF0000", googlemap.getColor(maximum));
    }

    /**
     * Tests the GoogleMaps.toHex function
     */
    @Test
    public void testToHex() {
        GoogleMap googlemap = new GoogleMap();
        int r = 255;
        int g = 255;
        int b = 255;
        assertEquals("#FFFFFF",GoogleMap.toHex(r,g,b));

        r = 186;
        g = 85;
        b = 211;
        assertEquals("#BA55D3",GoogleMap.toHex(r,g,b));
    }

}



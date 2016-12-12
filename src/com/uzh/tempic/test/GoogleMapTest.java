package com.uzh.tempic.test;

import com.uzh.tempic.client.view.GoogleMap;
import junit.framework.TestCase;
import org.junit.Test;

public class GoogleMapTest extends TestCase {

    @Test
    public void testGetColor() {
        GoogleMap googlemap = new GoogleMap();
        double minimum = -10;
        double maximum = 10;
        assertEquals("00FF00", googlemap.getColor(minimum));
        assertEquals("FF0000", googlemap.getColor(maximum));
    }
}



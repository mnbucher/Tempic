package com.uzh.tempic.test;
import com.uzh.tempic.shared.TemperatureData;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by Robin on 14.11.2016.
 */
public class TemperatureDataTest extends TestCase {

    /* Set a Test for the Temperature Data Class */
    public void testTemperatureData(){
        Date date = new Date(2010, 1, 1);
        Double avgTemperature = 10.0;
        Double avgTemperatureUncertainty = 1.0;
        String city = "TestCity";
        String country = "TestCountry";
        String latitude = "latitude";
        String longitude = "longitude";
        TemperatureData test = new TemperatureData(date, avgTemperature, avgTemperatureUncertainty, city, country, latitude, longitude);

        assertEquals(date, test.getDate());
        assertEquals(avgTemperature, test.getAvgTemperature(), 0.001);
        assertEquals(avgTemperatureUncertainty, test.getAvgTemperatureUncertainty(), 0.001);
        assertEquals(city, test.getCity());
        assertEquals(country, test.getCountry());
        assertEquals(latitude, test.getLatitude());
        assertEquals(longitude, test.getLongitude());
    }


}
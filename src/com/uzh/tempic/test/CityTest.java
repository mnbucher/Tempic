package com.uzh.tempic.test;

import com.uzh.tempic.shared.City;
import com.uzh.tempic.shared.Country;
import com.uzh.tempic.shared.TemperatureData;
import junit.framework.TestCase;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Robin on 14.11.2016.
 */
public class CityTest extends TestCase{
    /* Set a Test for the City Class */
    public void testCity(){
        String countryname = "countryname";
        double latitude = 1;
        double longitude = 1;
        Country country = new Country(countryname);
        ArrayList<TemperatureData> list = new ArrayList<TemperatureData>();

        City city = new City(latitude, longitude, country, list);

        assertEquals(latitude, city.getLatitude(), 0.01);
        assertEquals(longitude, city.getLongitude(), 0.01);
        assertEquals(country, city.getCountry());
    }

}
package com.uzh.tempic.test;
import com.google.gwt.junit.client.GWTTestCase;
import com.uzh.tempic.shared.TemperatureDataComparison;
import junit.framework.TestCase;


import java.util.Date;

public class TemperatureDataComparisonTest extends GWTTestCase {
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

    public void testTemperatureDataComparison(){
        int yearA = 2010;
        int yearB = 2013;
        Date dateA = new Date(yearA, 1, 1);
        Double avgTemperatureA = 10.0;
        Double avgTemperatureUncertaintyA = 1.0;
        Date dateB = new Date(yearB,1,1);
        Double avgTemperatureB = 5.0;
        Double avgTemperatureUncertaintyB = 0.5;
        String city = "TestCity";
        String country = "TestCountry";
        String latitude = "latitude";
        String longitude = "longitude";
        TemperatureDataComparison test = new TemperatureDataComparison(dateA, avgTemperatureA, avgTemperatureUncertaintyA,dateB,avgTemperatureB,avgTemperatureUncertaintyB, city, country, latitude, longitude);


        assertEquals(yearB - yearA, test.getYearDifference());
        assertEquals(Math.round((avgTemperatureB - avgTemperatureA)*100.0)/100.0, test.getTemperatureDifference());
        assertEquals(Math.abs((avgTemperatureB / avgTemperatureA) - 1), test.getTemperatureDifferencePercent());
        assertEquals("50%",test.getFormattedTemperatureDifferencePercent());
        assertEquals(dateA, test.getDateA());
        assertEquals(dateB, test.getDateB());
        assertEquals(avgTemperatureA, test.getAvgTemperatureA(), 0.001);
        assertEquals(avgTemperatureUncertaintyA, test.getAvgTemperatureUncertaintyA(), 0.001);
        assertEquals(avgTemperatureB, test.getAvgTemperatureB(), 0.001);
        assertEquals(avgTemperatureUncertaintyB, test.getAvgTemperatureUncertaintyB(), 0.001);
        assertEquals(yearA+1900, test.getYearA());
        assertEquals(yearB+1900, test.getYearB());
        assertEquals(city, test.getCity());
        assertEquals(country, test.getCountry());
        assertEquals(latitude, test.getLatitude());
        assertEquals(longitude, test.getLongitude());
    }


}
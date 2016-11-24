package com.uzh.tempic.test;

import com.uzh.tempic.server.TempicServiceImpl;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TempicException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class TempicServiceImplTest extends TestCase {
    /**
     * Tests the {@link TempicServiceImpl#getCountryNames()} method,
     * which should return all 49 country names from the database.
     *
     * @pre TempicServiceImpl and its dependencies must be available, the database must be working and data present
     * @post The test is passed if every distinct country name is returned
     */
    public void testGetCountryNames() {
        TempicServiceImpl tempicService = new TempicServiceImpl();
        try {
            ArrayList<String> countryNames = tempicService.getCountryNames();
            assertEquals(49, countryNames.size());

        } catch (TempicException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the {@link TempicServiceImpl#getTemperatureDataFiltered(ArrayList, int, int, double, int, String)} ()} method with
     * predefined test parameters, which should return exactly 9000 entries from the database.
     *
     * @pre TempicServiceImpl and its dependencies must be available, the database must be working and data present
     * @post The test is passed if exactly 9000 rows are returned
     */
    public void testGetTemperatureDataFiltered() {
        TempicServiceImpl tempicService = new TempicServiceImpl();
        ArrayList<String> countries = new ArrayList<String>();
        countries.addAll(Arrays.asList("China", "Brazil"));
        ArrayList<TemperatureData> temperatureData;
        try {
            temperatureData = tempicService.getTemperatureDataFiltered(countries, 1497, 2015, 3, 9000, "month");
            assertEquals(9000, temperatureData.size());
        } catch (TempicException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the {@link TempicServiceImpl#getTemperatureDataByYear(int)} ()} method with
     * a predefined test parameter, which should return exactly 100 entries from the database.
     *
     * @pre TempicServiceImpl and its dependencies must be available, the database must be working and data present
     * @post The test is passed if exactly 100 rows are returned
     */
    public void testGetTemperatureDataByYear() {
        TempicServiceImpl tempicService = new TempicServiceImpl();
        ArrayList<TemperatureData> temperatureData;
        try {
            temperatureData = tempicService.getTemperatureDataByYear(2012);
            assertEquals(100, temperatureData.size());
        } catch (TempicException e) {
            fail(e.getMessage());
        }
    }
}
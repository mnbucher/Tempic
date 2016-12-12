package com.uzh.tempic.test;

import com.google.gwt.junit.client.GWTTestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        CityTest.class,
        CountryTest.class,
        GoogleMapTest.class,
        TemperatureDataTest.class,
        TemperatureDataComparisonTest.class,
        TempicServiceImplTest.class,
})

/**
 * Test Suite which executes all our tests
 */
public class TempicTest {

}
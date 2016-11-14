package com.uzh.tempic.test;

import com.uzh.tempic.shared.Country;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Robin on 14.11.2016.
 */
public class CountryTest extends TestCase {
    @Test
     /* Set a Test for the Country Class */
    public void testgetName() {
        String name = "countryname";
        Country test = new Country(name);

        assertEquals(name,test.getName());

    }

}
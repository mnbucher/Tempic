package com.uzh.tempic.shared;

import java.util.ArrayList;

/**
 * Created by michaelziorjen on 01.11.16.
 */
public class City {
    private double latitude;
    private double longitude;
    private Country country;
    private ArrayList<TemperatureData> temperatureData;

    public City(double latitude, double longitude,Country country, ArrayList<TemperatureData> temperatureData) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.temperatureData = temperatureData;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public Country getCountry() {
        return this.country;
    }

}

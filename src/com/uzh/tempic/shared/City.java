package com.uzh.tempic.shared;

/**
 * Created by michaelziorjen on 01.11.16.
 */
public class City {
    private double latitude;
    private double longitude;
    private Country country;
    private ArrayList<TemperatureData> temperatureData;

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

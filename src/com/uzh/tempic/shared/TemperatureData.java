package com.uzh.tempic.shared;

import java.util.Date;

/**
 * Created by michaelziorjen on 01.11.16.
 */
public class TemperatureData {

    private double averageTemperatureUncertainity;
    private double averageTemperature;
    private Date date;
    private City city;

    public double getUncertainity() {
        return this.averageTemperatureUncertainity;
    }

    public double getAverageTemperature() {
        return this.averageTemperature;
    }

    public City getCity() {
        return this.city;
    }

    public Date getDate() {
        return this.date;
    }
}

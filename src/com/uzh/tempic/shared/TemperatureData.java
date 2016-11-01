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

    public TemperatureData(double averageTemperatureUncertainity, double averageTemperature, Date date) {
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUncertainity = averageTemperatureUncertainity;
        this.city = null;
        this.date = date;
    }

    public TemperatureData(double averageTemperatureUncertainity, double averageTemperature,  Date date, City city) {
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUncertainity = averageTemperatureUncertainity;
        this.city = city;
        this.date = date;
    }


    public double getUncertainity() {
        return this.averageTemperatureUncertainity;
    }

    public double getAverageTemperature() {
        return this.averageTemperature;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getDate() {
        return this.date;
    }
}

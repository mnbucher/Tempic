package com.uzh.tempic.shared;

import java.util.Date;

public class TemperatureData {

    private double averageTemperatureUncertainty;
    private double averageTemperature;
    private Date date;
    private City city;

    public TemperatureData(double averageTemperatureUncertainty, double averageTemperature, Date date) {
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUncertainty = averageTemperatureUncertainty;
        this.city = null;
        this.date = date;
    }

    public TemperatureData(double averageTemperatureUncertainty, double averageTemperature,  Date date, City city) {
        this.averageTemperature = averageTemperature;
        this.averageTemperatureUncertainty = averageTemperatureUncertainty;
        this.city = city;
        this.date = date;
    }


    public double getUncertainty() {
        return this.averageTemperatureUncertainty;
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

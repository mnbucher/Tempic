package com.uzh.tempic.shared;

import java.sql.Date;

public class TemperatureData {
    private Date date;
    private Double avgTemperature;
    private Double avgTemperatureUncertainty;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;

    public TemperatureData(Date date, Double avgTemperature, Double avgTemperatureUncertainty,
                           String city, String country, Double latitude, Double longitude) {
        this.date = date;
        this.avgTemperature = avgTemperature;
        this.avgTemperatureUncertainty = avgTemperatureUncertainty;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public Double getAvgTemperatureUncertainty() {
        return avgTemperatureUncertainty;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
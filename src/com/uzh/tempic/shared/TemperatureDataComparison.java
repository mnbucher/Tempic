package com.uzh.tempic.shared;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * Created by michaelziorjen on 30/11/16.
 */
public class TemperatureDataComparison extends TemperatureData {
    private double avgTemperatureB;
    private double avgTemperatureUncertaintyB;
    private Date dateB;

    public TemperatureDataComparison() {

    }

    public TemperatureDataComparison(Date dateA, double avgTemperatureA, double avgTemperatureUncertaintyA, Date dateB, double avgTemperatureB, double avgTemperatureUncertaintyB,
                              String city, String country, String latitude, String longitude) {
        super(dateA, avgTemperatureA, avgTemperatureUncertaintyA, city, country, latitude, longitude);
        this.avgTemperatureB = avgTemperatureB;
        this.avgTemperatureUncertaintyB = avgTemperatureUncertaintyB;
        this.dateB = dateB;
    }

    /** Returns the difference between the to compared dates in years
     * @pre dateB is more recent than dateA
     * @return the difference between the two dates in years
     */
    public int getYearDifference() {
        return (this.getYearB() - this.getYearA());
    }

    /** Returns the absolute temperature difference between the two compared dates
     * @return double absolute difference in degree celsius
     */
    public double getTemperatureDifference() {
        return (Math.round((this.avgTemperatureB - this.getAvgTemperatureA())*100.0)/100.0);
    }

    /** Returns the temperature difference in percent between the two compared dates
     *
     * @return double the difference in percent compared to the first date's temperature
     */
    public double getTemperatureDifferencePercent() {
        return Math.abs((this.getAvgTemperatureB() / this.getAvgTemperatureA()) - 1);
    }

    public String getFormattedTemperatureDifferencePercent() {
        double roundedValue = Math.round(this.getTemperatureDifferencePercent() * 100.0) / 100.0;
        roundedValue = roundedValue * 100; // Make it appear as percent value
        String percentDiff = String.valueOf(roundedValue);
        percentDiff = percentDiff.concat("%");
        return percentDiff;
    }

    public Date getDateA() {
        return this.getDate();
    }

    public double getAvgTemperatureA() {
        return Math.round(this.getAvgTemperature()*100.0)/100.0;
    }

    public double getAvgTemperatureUncertaintyA() {
        return this.getAvgTemperatureUncertainty();
    }

    public double getAvgTemperatureB() {
        return Math.round(this.avgTemperatureB*100.0)/100.0;
    }

    public double getAvgTemperatureUncertaintyB() {
        return this.avgTemperatureUncertaintyB;
    }

    public Date getDateB() {
        return this.dateB;
    }

    public int getYearA() {
        return this.getYear();
    }
    public int getYearB() {
        return Integer.parseInt(DateTimeFormat.getFormat( "yyyy" ).format( this.dateB ));
    }


}

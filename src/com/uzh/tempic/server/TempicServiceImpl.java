package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.TempicService;
import com.uzh.tempic.shared.TemperatureData;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TempicServiceImpl extends RemoteServiceServlet implements TempicService {

    // Implementation of sample interface method
    public String getMessage(String msg){
        String output = "Hello";
        output = output.concat(testConnection());
        return output.concat(msg);
    }

    public ArrayList<TemperatureData> getTemperatureData() {
        ArrayList<TemperatureData> temperatureDataArrayList = new ArrayList<>();

        String selectSql = "SELECT * FROM temperature_data ORDER BY dt ASC LIMIT 500";
        String url = "jdbc:mysql://104.199.57.151/tempic";
        try { Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            //return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");

            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            while (rs.next()) {
                TemperatureData tempEntry = new TemperatureData(
                        rs.getDate("dt"),
                        rs.getDouble("average_temperature"),
                        rs.getDouble("average_temperature_uncertainty"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("latitude"),
                        rs.getString("longitude"));
                temperatureDataArrayList.add(tempEntry);
            }
        } catch(SQLException e) {
            //return null;
        }
        //ArrayList<TemperatureData> temperatureDataArrayList;
        //temperatureDataArrayList = new ArrayList<TemperatureData>();
        //temperatureDataArrayList.add(new TemperatureData(new Date(System.currentTimeMillis()), 1239.32, 12304.23, "t1234est", "coun4213try", 5534.12, 123.54234));
        return temperatureDataArrayList;
    }
    /*
        Returns all the Names of the Countries from the DB in Ascending Order.
     */
    public ArrayList<String> getCountryNames() {
        ArrayList<String> countryNames = new ArrayList<>();

        String selectSql = "SELECT country FROM temperature_data GROUP BY country ASC";
        String url = "jdbc:mysql://104.199.57.151/tempic";
        try { Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            //return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");

            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("country");
                countryNames.add(countryName);
            }
        } catch(SQLException e) {

        }
        return countryNames;
    }
    /*
        Doesn't work yet because of IN statement
     */
    public ArrayList<TemperatureData> getDataForCountries(ArrayList<String> countryNames) {
        ArrayList<TemperatureData> temperatureData = new ArrayList<>();


        String inStatement = "";
        for(int i = 0; i < countryNames.size() - 1; i++) {
            inStatement = inStatement.concat("'" + countryNames.get(i) + "',");
        }
        inStatement = inStatement.concat("'" + countryNames.get(countryNames.size()-1) + "'");
        String selectSql = "SELECT * FROM temperature_data WHERE country IN(" + inStatement + ") ORDER BY country ASC, dt ASC";
        String url = "jdbc:mysql://104.199.57.151/tempic";
        try { Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            //return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
            PreparedStatement getData = conn.prepareStatement(selectSql);
            for(int i = 0; i < countryNames.size(); i++) {
                //getData.setString(i,countryNames.get(i));
            }

            ResultSet rs = getData.executeQuery();
            while (rs.next()) {
                TemperatureData tempEntry = new TemperatureData(
                        rs.getDate("dt"),
                        rs.getDouble("average_temperature"),
                        rs.getDouble("average_temperature_uncertainty"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("latitude"),
                        rs.getString("longitude"));
                temperatureData.add(tempEntry);
            }
        } catch(SQLException e) {

        }
        return temperatureData;
    }

    public String testConnection()  {
        String output = "Shit didn't work";
        final String selectSql = "SELECT * FROM temperature_data ORDER BY dt DESC LIMIT 10";
        //String url = System.getProperty("ae-cloudsql.cloudsql-database-url");
        // old url jdbc:google:mysql://tempic-uzh:europe-west1:tempic-db/tempic-db?user=root&amp;password=T3mp!C_Y0L0
        String url = "jdbc:mysql://104.199.57.151/tempic";

        /*
        Load the class that provides the new "jdbc:google:mysql://" prefix.
        TODO: Execute only on server.
        */
        /*try {
            Class.forName("com.mysql.jdbc.GoogleDriver");

        } catch(ClassNotFoundException e) {
            return  "ClassNotFoundException: "+ e.getMessage();
        };*/

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch(ClassNotFoundException e) {
            return  "ClassNotFoundException: "+ e.getMessage();
        }


        try {
            // on Server:
            //Connection conn = DriverManager.getConnection(url);

            // When testing locally:
            Connection conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");

            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            output = "Data in Database ";
            while (rs.next()) {
                String city = rs.getString("city");
                String country = rs.getString("country");
                Date dateTime = rs.getDate("dt");
                output = output.concat("City: " + city + " Country: " + country + "" + "dateTime: " +dateTime+"\n");
            }
        } catch(SQLException e) {
            return  "SQLException: " + e.getMessage();
        }
        return output;
    }
}
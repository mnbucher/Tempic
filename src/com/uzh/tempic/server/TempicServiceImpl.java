package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.TempicService;
import com.uzh.tempic.shared.TemperatureData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TempicServiceImpl extends RemoteServiceServlet implements TempicService {

    // Implementation of sample interface method
    public String getMessage(String msg){
        String output = "Hello";
        output = output.concat("test");
        return output.concat(msg);
    }

    /**
     * Returns a SQL Connection object which can be used to
     * interact with the database.
     *
     * @return the Connection object
     */
    private Connection getDBConnection() {
        String url = "jdbc:mysql://104.199.57.151/tempic";
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
        } catch(ClassNotFoundException | SQLException e) {
            // Handle error in frontend, by checking for null value
        }
        return conn;
    }

    public ArrayList<TemperatureData> getTemperatureData() {
        ArrayList<TemperatureData> temperatureDataArrayList = new ArrayList<>();

        String selectSql = "SELECT * FROM temperature_data ORDER BY dt ASC LIMIT 500";
        try {
            Connection conn = getDBConnection();
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
        } catch(SQLException e) {}
        return temperatureDataArrayList;
    }
    /*
        Returns all the Names of the Countries from the DB in Ascending Order.
     */
    public ArrayList<String> getCountryNames() {
        ArrayList<String> countryNames = new ArrayList<>();
        String selectSql = "SELECT country FROM temperature_data GROUP BY country ASC";

        try {
            Connection conn = getDBConnection();
            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("country");
                countryNames.add(countryName);
            }
        } catch(SQLException e) {}
        return countryNames;
    }

    public ArrayList<TemperatureData> getDataForCountries(ArrayList<String> countryNames) throws Throwable {
        ArrayList<TemperatureData> temperatureData = new ArrayList<>();


        String inStatement = "";
        for(int i = 0; i < countryNames.size() - 1; i++) {
            inStatement = inStatement.concat("'" + countryNames.get(i) + "',");
        }
        inStatement = inStatement.concat("'" + countryNames.get(countryNames.size()-1) + "'");
        //String selectSql = "SELECT * FROM temperature_data WHERE country IN(" + inStatement + ") ORDER BY country ASC, dt ASC";
        String selectSql = "SELECT * FROM temperature_data WHERE country IN(" + inStatement + ") AND dt BETWEEN '2000-01-01' AND '2016-11-11' ORDER BY country ASC, dt ASC LIMIT 100";

        String url = "jdbc:mysql://104.199.57.151/tempic";
        try { Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw(new Throwable("Error:" + e.getMessage()));
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

    public ArrayList<TemperatureData> getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty) {
        ArrayList<TemperatureData> temperatureData = new ArrayList<>();
        String inStatement = "";
        for(int i = 0; i < countryNames.size() - 1; i++) {
            inStatement = inStatement.concat("'" + countryNames.get(i) + "',");
        }
        inStatement = inStatement.concat("'" + countryNames.get(countryNames.size()-1) + "'");
        String selectSql = "SELECT * FROM temperature_data WHERE country IN(" + inStatement + ") AND " +
                "dt BETWEEN '" + from + "-01-01' AND '" + to + "-12-31' AND " +
                "average_temperature_uncertainty <= '" + uncertainty + "'" +
                "ORDER BY country ASC, dt ASC LIMIT 100";
        try {
            Connection conn = getDBConnection();
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
                temperatureData.add(tempEntry);
            }
        } catch(SQLException e) {}
        return temperatureData;
    }
}
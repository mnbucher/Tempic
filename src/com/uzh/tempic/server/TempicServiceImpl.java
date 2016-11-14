package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.TempicService;
import com.uzh.tempic.shared.TemperatureData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TempicServiceImpl extends RemoteServiceServlet implements TempicService {
    /**
     * Returns a SQL Connection object which can be used to interact with the database.
     *
     * @return the Connection object
     */
    private Connection getDBConnection() throws Throwable {
        String url = "jdbc:mysql://104.199.57.151/tempic";
        Connection conn = null;
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = "jdbc:google:mysql://tempic-uzh:europe-west1:tempic-db/tempic"; 
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
            } catch (ClassNotFoundException e) {
                throw new Throwable("Error loading Google JDBC Driver", e);
            } catch (SQLException e) {
                throw new Throwable("SQL Error: " + e.getMessage(),e);
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = "jdbc:mysql://104.199.57.151/tempic";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
            } catch(SQLException e) {
                throw new Throwable("SQL error", e);
            }
        }

        return conn;
    }

    /**
     * Executes the given (SQL) query and returns a ready-to-use
     * ArrayList of TemperatureData.
     *
     * @pre the query parameter is a valid SQL statement
     * @post the return value tempData is the SQLs result
     * @param query the sql statement to execute
     * @return ArrayList of TemperatureData from the DB
     */
    private ArrayList<TemperatureData> getTemperatureDataByQuery(String query) throws Throwable {
        ArrayList<TemperatureData> tempData = new ArrayList<>();
        try {
            Connection conn = getDBConnection();
            ResultSet rs = conn.prepareStatement(query).executeQuery();
            while (rs.next()) {
                TemperatureData tempEntry = new TemperatureData(
                        rs.getDate("dt"),
                        rs.getDouble("average_temperature"),
                        rs.getDouble("average_temperature_uncertainty"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("latitude"),
                        rs.getString("longitude"));
                tempData.add(tempEntry);
            }
        } catch(SQLException e) {

        }
        return tempData;
    }

    /**
     * Returns a ArrayList of names from all countries from the DB in ascending order.
     *
     * @return a ArrayList of Strings containing all country names
     */
    public ArrayList<String> getCountryNames() throws Throwable {
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

    /**
     * Creates and executes a sql query and returns the result
     * as a TemperatureData ArrayList using the provided parameters.
     *
     * @pre -
     * @post the returned value corresponds to the actual result of the query formed with the parameters
     * @param countryNames A ArrayList containing all countryNames as Strings
     * @param from The starting year of the data set
     * @param to The last year (inclusive) of the data set
     * @param uncertainty The uncertainty of the temperature data entry
     * @param limitTo The amount of results the query should be limited to
     * @return A ArrayList containing all relevant data according to the parameters
     */
    public ArrayList<TemperatureData> getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo) throws Throwable {
        String inStatement = "";
        for(int i = 0; i < countryNames.size() - 1; i++) {
            inStatement = inStatement.concat("'" + countryNames.get(i) + "',");
        }
        inStatement = inStatement.concat("'" + countryNames.get(countryNames.size()-1) + "'");
        String sqlQuery = "SELECT * FROM temperature_data WHERE country IN(" + inStatement + ") AND " +
                "dt BETWEEN '" + from + "-01-01' AND '" + to + "-12-31' AND " +
                "average_temperature_uncertainty <= '" + uncertainty + "'" +
                "ORDER BY country ASC, dt ASC " +
                "LIMIT " + limitTo;

        return getTemperatureDataByQuery(sqlQuery);
    }
}
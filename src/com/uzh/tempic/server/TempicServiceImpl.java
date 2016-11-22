package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.TempicService;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TempicException;

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
    private Connection getDBConnection() throws TempicException {
        String url;
        Connection conn = null;
        if(System.getProperty("com.google.appengine.runtime.version") != null && System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = "jdbc:google:mysql://tempic-uzh:europe-west1:tempic-db/tempic";
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
            } catch (ClassNotFoundException e) {
                throw new TempicException("Error loading Google JDBC Driver: " + e.getMessage());
            } catch (SQLException e) {
                throw new TempicException("SQL Error: " + e.getMessage());
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = "jdbc:mysql://104.199.57.151/tempic";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url,"root","T3mp!C_Y0L0");
            }catch (ClassNotFoundException e) {
                throw new TempicException("Error loading Google JDBC Driver: " + e.getMessage());
            } catch(SQLException e) {
                throw new TempicException("SQL Error: " + e.getMessage());
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
    private ArrayList<TemperatureData> getTemperatureDataByQuery(String query) throws TempicException {
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
            rs.close();
            conn.close();
        } catch(TempicException e) {
            throw e;
        } catch(SQLException e) {
            throw new TempicException("SQL Error: " + e.getMessage());
        }
        return tempData;
    }

    /**
     * Returns a ArrayList of names from all countries from the DB in ascending order.
     *
     * @return a ArrayList of Strings containing all country names
     */
    public ArrayList<String> getCountryNames() throws TempicException {
        ArrayList<String> countryNames = new ArrayList<>();
        String selectSql = "SELECT country FROM temperature_data GROUP BY country ASC";
        try {
            Connection conn = getDBConnection();
            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("country");
                countryNames.add(countryName);
            }
            rs.close();
            conn.close();
        } catch(TempicException e) {
            throw e;
        } catch(SQLException e) {
            throw new TempicException("SQL Error: " + e.getMessage());
        }
        return countryNames;
    }

    /**
     * Returns a ArrayList of names from all cities from the DB in ascending order.
     *
     * @return a ArrayList of Strings containing all city names
     */
    public ArrayList<String> getCityNames() throws TempicException {
        ArrayList<String> cityNames = new ArrayList<>();
        String selectSql = "SELECT city FROM temperature_data GROUP BY city ASC";
        try {
            Connection conn = getDBConnection();
            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            while (rs.next()) {
                String cityName = rs.getString("city");
                cityNames.add(cityName);
            }
            rs.close();
            conn.close();
        } catch(TempicException e) {
          throw e;
        } catch(SQLException e) {
            throw new TempicException("SQL Error: " + e.getMessage());
        }
        return cityNames;
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
    public ArrayList<TemperatureData> getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo) throws TempicException {
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

    /** Gets the average temperature values for each city for one year
     * @pre year >= 1743 and year <= 2013
     * @post the returned value corresponds to the actual result of the query formed with the parameter
     * @param year the year of which the temperature data should be returned
     * @return An ArrayList containing all the relevant data according to the parameter
     * **/
    public ArrayList<TemperatureData> getTemperatureDataByYear(int year) throws TempicException {
        String sqlQuery = "SELECT MAX(dt) AS dt, city, country, latitude, longitude, AVG(average_temperature) AS average_temperature, AVG(average_temperature_uncertainty) AS average_temperature_uncertainty " +
                "FROM temperature_data WHERE YEAR(dt) = '" + year + "' " +
                "GROUP BY city, country, latitude, longitude, YEAR(dt) ORDER BY city ASC";
        return getTemperatureDataByQuery(sqlQuery);
    }

    /** Gets the average temperature value for each city for the first year in the dataset
     * @pre -
     * @post the returned value corresponds to the actual result of the query formed with the parameter
     * @return An ArrayList containing the average temperature for the first measured year
     * **/
    public ArrayList<TemperatureData> getTemperatureDataFirstRecorded() throws TempicException {
        String sqlQuery = "SELECT  MAX(dt) AS dt, td.country, td.city, td.latitude, td.longitude, AVG(td.average_temperature) as average_temperature, AVG(td.average_temperature_uncertainty) AS average_temperature_uncertainty " +
                "FROM temperature_data td " +
                "INNER JOIN (SELECT city, MIN(YEAR(dt)) minYR FROM temperature_data GROUP BY city) oldTD " +
                "ON td.city = oldTD.city AND YEAR(td.dt) = oldTD.minYR " +
                "GROUP BY td.city, td.country, td.longitude, td.latitude, YEAR(td.dt) ORDER BY city ASC";

        return getTemperatureDataByQuery(sqlQuery);
    }

    /** Returns the difference between the specified year's average temperature and the first measured year for each city
     *  and the temperature change average of the two compared years
     * @pre year >= 1743 and year <= 2013
     * @post the returned value corresponds to the actual result of the query formed with the parameter
     * @param year the year to which the first year should be compared
     * @return An ArrayList containing all the temperature data and the corresponding differences / averages
     * **/

    // TODO: Check if difference is calculated correctly
    // TODO: Do comparison in SQL so no "mismatch" error can occur
    public ArrayList<TemperatureData> getTemperatureDataDifference(int year) throws TempicException {
        String sqlQuery = "SELECT MAX(dt) AS dt, td.country, td.city, td.latitude, td.longitude, AVG(td.average_temperature) as average_temperature, AVG(td.average_temperature_uncertainty) AS average_temperature_uncertainty, MAX(newTD.dt_new) as dt_new, MAX(newTD.average_temperature_new) as average_temperature_new, MAX(newTD.average_temperature_uncertainty_new) as average_temperature_uncertainty_new " +
                "FROM temperature_data td " +
                "INNER JOIN (SELECT city, MIN(YEAR(dt)) minYR FROM temperature_data GROUP BY city) oldTD " +
                "ON td.city = oldTD.city AND YEAR(td.dt) = oldTD.minYR " +
                "INNER JOIN (SELECT city, MAX(dt) as dt_new, AVG(average_temperature) AS average_temperature_new, AVG(average_temperature_uncertainty) AS average_temperature_uncertainty_new FROM temperature_data WHERE YEAR(dt) = '" + year + "' GROUP BY city, YEAR(dt)) newTD " +
                "ON td.city = newTD.city " +
                "GROUP BY td.city, td.country, td.longitude, td.latitude, YEAR(td.dt) ORDER BY city ASC";


        ArrayList<TemperatureData> tempData = new ArrayList<>();
        try {
            Connection conn = getDBConnection();
            ResultSet rs = conn.prepareStatement(sqlQuery).executeQuery();
            while (rs.next()) {
                TemperatureData tempEntry = new TemperatureData(
                        rs.getDate("dt"),
                        rs.getDouble("average_temperature") - rs.getDouble("average_temperature_new"),
                        (rs.getDouble("average_temperature_uncertainty") + rs.getDouble("average_temperature_uncertainty_new")) / 2,
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("latitude"),
                        rs.getString("longitude"));
                tempData.add(tempEntry);
            }
            rs.close();
            conn.close();
        } catch(TempicException e) {
            throw e;
        } catch(SQLException e) {
            throw new TempicException("SQL Error: " + e.getMessage());
        }
        return tempData;
    }
}
package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.TempicService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TempicServiceImpl extends RemoteServiceServlet implements TempicService {

    // Implementation of sample interface method
    public String getMessage(String msg){
        String output;
        output = testConnection();
        return output;
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
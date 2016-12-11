package com.uzh.tempic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TemperatureDataComparison;
import com.uzh.tempic.shared.TempicException;

import java.util.ArrayList;

@RemoteServiceRelativePath("tempicService")
public interface TempicService extends RemoteService {
    ArrayList<String> getCountryNames() throws TempicException;
    ArrayList<String> getCityNames() throws TempicException;
    ArrayList<TemperatureData> getTemperatureDataFiltered(ArrayList<String> countryNames, String searchBy, int from, int to, double uncertainty, int limitTo, String aggregateBy, String groupByCityOrCountry) throws TempicException;
    ArrayList<TemperatureData> getTemperatureDataByYear(int year) throws TempicException;
    ArrayList<TemperatureDataComparison> getTemperatureDataDifference(int year) throws TempicException;

    /**
     * Utility/Convenience class.
     * Use TempicService.App.getInstance() to access static instance of tempicServiceAsync
     */
    class App {
        private static TempicServiceAsync ourInstance = GWT.create(TempicService.class);

        public static synchronized TempicServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

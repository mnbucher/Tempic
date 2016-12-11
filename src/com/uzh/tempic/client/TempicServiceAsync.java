package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TemperatureDataComparison;

import java.util.ArrayList;

public interface TempicServiceAsync {
    void getCountryNames(AsyncCallback<ArrayList<String>> callback);
    void getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo, String aggregateBy, String groupByCityOrCountry, AsyncCallback<ArrayList<TemperatureData>> async);
    void getTemperatureDataByYear(int year, AsyncCallback<ArrayList<TemperatureData>> async);
    void getTemperatureDataDifference(int year, AsyncCallback<ArrayList<TemperatureDataComparison>> async);


}

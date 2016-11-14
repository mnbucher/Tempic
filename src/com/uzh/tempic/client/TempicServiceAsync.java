package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public interface TempicServiceAsync {
    void getTemperatureData(AsyncCallback<ArrayList<TemperatureData>> callback);
    void getCountryNames(AsyncCallback<ArrayList<String>> callback);
    void getDataForCountries(ArrayList<String> countryNames, AsyncCallback<ArrayList<TemperatureData>> callback);
    void getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, AsyncCallback<ArrayList<TemperatureData>> async);
}

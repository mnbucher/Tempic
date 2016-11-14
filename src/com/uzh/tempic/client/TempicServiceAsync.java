package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public interface TempicServiceAsync {
    void getCountryNames(AsyncCallback<ArrayList<String>> callback) throws Throwable;
    void getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo, AsyncCallback<ArrayList<TemperatureData>> async) throws Throwable;
}

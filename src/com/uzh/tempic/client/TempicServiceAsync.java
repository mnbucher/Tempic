package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public interface TempicServiceAsync {
    void getCountryNames(AsyncCallback<ArrayList<String>> callback);
    void getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo, AsyncCallback<ArrayList<TemperatureData>> async);
}

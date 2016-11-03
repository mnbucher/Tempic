package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

public interface TempicServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void getTemperatureData(AsyncCallback<ArrayList<TemperatureData>> callback);
}

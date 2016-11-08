package com.uzh.tempic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;

@RemoteServiceRelativePath("tempicService")
public interface TempicService extends RemoteService {
    /** Sample interface method of remote interface **/
    String getMessage(String msg);

    ArrayList<TemperatureData> getTemperatureData();
    ArrayList<String> getCountryNames();
    ArrayList<TemperatureData> getDataForCountries(ArrayList<String> countryNames) throws Throwable;

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

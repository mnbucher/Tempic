package com.uzh.tempic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uzh.tempic.shared.TemperatureData;

import javax.servlet.ServletException;
import java.sql.Array;
import java.util.ArrayList;

@RemoteServiceRelativePath("tempicService")
public interface TempicService extends RemoteService {
    ArrayList<String> getCountryNames() throws Throwable;
    ArrayList<TemperatureData> getTemperatureDataFiltered(ArrayList<String> countryNames, int from, int to, double uncertainty, int limitTo) throws Throwable;

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

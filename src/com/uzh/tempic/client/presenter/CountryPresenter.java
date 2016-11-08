package com.uzh.tempic.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class CountryPresenter implements Presenter {
    public interface Display {
        void setCountryNames(ArrayList<String> countryNames);
        void setTemperatureData(ArrayList<TemperatureData> result);
        Widget asWidget();
    }

    private final TempicServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public CountryPresenter(TempicServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }

    /*
        Binds the interactions in the view to the presenter / eventbus
     */
    public void bind() {

    }
    /*
        Renders the view (adds the view to the root DOM Element specified by the AppController
     */
    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchCountryData();
        fetchTemperatureData();
    }
    /*
        Gets the data from the model
     */
    private void fetchCountryData() {
        rpcService.getCountryNames(new AsyncCallback<ArrayList<String>>() {
            public void onSuccess(ArrayList<String> result) {
                // pass the Data to the View
                display.setCountryNames(result);
            }
            public void onFailure(Throwable caught) {
                Window.alert("Unable to fetch the country names.");
            }
        });
    }

    private void fetchTemperatureData() {
        ArrayList<String> defaultNames = new ArrayList<String>();
        defaultNames.addAll(Arrays.asList("China", "Chile", "Brazil", "Burma"));
        rpcService.getDataForCountries(defaultNames, new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {
                Logger logger = Logger.getLogger("NameOfYourLogger");
                logger.log(SEVERE, result.toString());

                display.setTemperatureData(result);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error: " + caught.getMessage());
            }
        });
    }

}

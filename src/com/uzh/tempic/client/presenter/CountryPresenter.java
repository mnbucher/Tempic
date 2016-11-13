package com.uzh.tempic.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryPresenter implements Presenter {
    public interface Display {
        void setCountryNames(ArrayList<String> countryNames);
        void setTemperatureData(ArrayList<TemperatureData> result);
        HasClickHandlers getFilterButton();
        ListBox getCountryListBox();
        ListBox getFromYearListBox();
        ListBox getToYearListBox();
        ListBox getUncertaintyListBox();
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
        display.getFilterButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                /* Since GWT doesn't return more than one element from the list box we need to iterate trough all of the items in the list box */
                ListBox countryListBox = display.getCountryListBox();
                ListBox fromYearListBox = display.getFromYearListBox();
                ListBox toYearListBox = display.getToYearListBox();
                ListBox uncertaintyListBox = display.getUncertaintyListBox();

                ArrayList<String> selectedValues = new ArrayList<>();
                int fromYear = Integer.parseInt(fromYearListBox.getSelectedValue());
                int toYear = Integer.parseInt(toYearListBox.getSelectedValue());
                double uncertainty = Double.parseDouble(uncertaintyListBox.getSelectedValue());

                for (int i = 0, l = countryListBox.getItemCount(); i < l; i++) {
                    if (countryListBox.isItemSelected(i)) {
                        selectedValues.add(countryListBox.getValue(i));
                    }
                }
                if(selectedValues.size() == 0) {
                    Window.alert("Please select at least one country.");
                } else if(toYear < fromYear || fromYear > toYear) {
                    Window.alert("Please select a valid time range.");
                } else {

                    Window.alert("Countries: " + selectedValues.toString() + " From: " + fromYear + " To: " + toYear + " Uncertainty: " + uncertainty);
                    fetchTemperatureDataFiltered(selectedValues, fromYear, toYear, uncertainty);
                }
            }
        });
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
    private void fetchTemperatureData(ArrayList<String> countries, int fromYear, int toYear) {

    }
    private void fetchTemperatureData(String country, int fromYear, int toYear) {
        ArrayList countries;
        countries = new ArrayList<String>();
        countries.add(country);
        this.fetchTemperatureData(countries,fromYear,toYear);
    }

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
                display.setTemperatureData(result);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error: " + caught.getMessage());
            }
        });
    }

    private void fetchTemperatureDataFiltered(ArrayList<String> countries, int from, int to, double uncertainty) {
        rpcService.getTemperatureDataFiltered(countries, from, to, uncertainty, new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {
                display.setTemperatureData(result);
            }
            public void onFailure(Throwable caught) { Window.alert("An error occurred while fetching the filtered temperature data:" + caught.getMessage()); }
        });
    }
}

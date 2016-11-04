package com.uzh.tempic.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelziorjen on 04.11.16.
 */
public class CountryPresenter implements Presenter {
    List<TemperatureData> temperatureData;
    public interface Display {
        HasClickHandlers getList();
        void setData(List<String> data);
        void setTemperatureTableData(ArrayList<TemperatureData> temperatureData);
        int getClickedRow(ClickEvent event);
        List<Integer> getSelectedRows();
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
    }
    /*
        Gets the data from the model
     */
    private void fetchCountryData() {
        rpcService.getTemperatureData(new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {

                // pass the Data to the View
                display.setTemperatureTableData(result);
            }
            public void onFailure(Throwable caught) {
                Window.alert("Unable to fetch the worlds temperature data");
            }
        });
    }

}

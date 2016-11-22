/*


    IMPORTANT: This class is still under construction (WIP).


 */

package com.uzh.tempic.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;
import com.uzh.tempic.client.presenter.Presenter;
import com.uzh.tempic.shared.TemperatureData;
import com.uzh.tempic.shared.TempicException;

import java.util.ArrayList;
import java.util.List;

public class WorldMapPresenter implements Presenter {

    public interface Display {
        HasClickHandlers getYearSlider();
        void setTemperatureData(ArrayList<TemperatureData> temperatureData);
        Widget asWidget();
    }

    private final TempicServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public WorldMapPresenter(TempicServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }


    // Binds the interactions in the view to the presenter / eventbus

    public void bind() {

        display.getYearSlider().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // eventBus.fireEvent(new AddContactEvent());
            }
        });

    }


    // Renders the view

    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchWorldMapData();
    }


    // Gets the data from the model

    private void fetchWorldMapData() {
       rpcService.getTemperatureDataByYear(2012,new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {
                display.setTemperatureData(result);
            }

            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });

    }

}

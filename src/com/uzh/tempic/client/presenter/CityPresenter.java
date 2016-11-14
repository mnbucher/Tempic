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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;
import com.uzh.tempic.shared.TemperatureData;

import java.util.ArrayList;
import java.util.Arrays;

public class CityPresenter implements Presenter {

    public interface Display {
      /*  void setCountryNames(ArrayList<String> countryNames);
        void setTemperatureData(ArrayList<TemperatureData> result);
        HasClickHandlers getFilterButton();
        ListBox getCountryListBox();
        ListBox getFromYearListBox();
        ListBox getToYearListBox();
        ListBox getUncertaintyListBox();*/
        Widget asWidget();
    }

    private final TempicServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public CityPresenter(TempicServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }

    // TODO: IMPLEMENT THIS
    public void bind(){

    }

    // TODO: IMPLEMENT THIS
    public void fetchCityData(){

    }

    // TODO: IMPLEMENT THIS
    public void fetchTemperatureData(){

    }

    /*
        Renders the view (adds the view to the root DOM Element specified by the AppController
     */
    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchCityData();
        fetchTemperatureData();
    }

}

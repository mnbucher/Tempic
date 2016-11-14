/*


    IMPORTANT: This class is still under construction (WIP).

    We don't display the section «Dashboard» in the Menu, so it isn't accessible for the user at the moment.


 */

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
import java.util.Arrays;
import java.util.List;

public class WorldDashboardPresenter implements Presenter {
    List<TemperatureData> temperatureData;
    public interface Display {
       // HasClickHandlers getAddButton();
       // HasClickHandlers getDeleteButton();
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

    public WorldDashboardPresenter(TempicServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }

    /*
        Binds the interactions in the view to the presenter / eventbus
     */
    public void bind() {
        /*display.getAddButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // eventBus.fireEvent(new AddContactEvent());
            }
        });

        display.getDeleteButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // deleteSelectedContacts();
            }
        });*/
    }
    /*
        Renders the view
     */
    public void go(final HasWidgets container) throws Throwable {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchWorldDashboardData();
    }
    /**
     * Calls the TempicService with a predefined set of parameters
     * to asynchronously load and display a initial set of data.
     */
    private void fetchWorldDashboardData() throws Throwable {
        ArrayList<String> initialCountries = new ArrayList<String>();
        initialCountries.addAll(Arrays.asList("Angola"));
        rpcService.getTemperatureDataFiltered(initialCountries, 1900, 2000, 3, 100, new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {
                display.setTemperatureTableData(result);
            }
            public void onFailure(Throwable caught) { Window.alert("An error occurred while fetching the worlds filtered temperature data:" + caught.getMessage()); }
        });
    }

}

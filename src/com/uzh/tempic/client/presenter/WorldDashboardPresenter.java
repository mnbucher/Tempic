package com.uzh.tempic.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.uzh.tempic.client.TempicServiceAsync;

import java.util.List;

public class WorldDashboardPresenter implements Presenter {
    public interface Display {
        HasClickHandlers getAddButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getList();
        void setData(List<String> data);
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
        display.getAddButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // eventBus.fireEvent(new AddContactEvent());
            }
        });

        display.getDeleteButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // deleteSelectedContacts();
            }
        });
    }
    /*
        Renders the view
     */
    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchWorldDashboardData();
    }
    /*
        Gets the data from the model
     */
    private void fetchWorldDashboardData() {
       /* rpcService.getWorldDashboardData(new AsyncCallback<ArrayList<TemperatureData>>() {
            public void onSuccess(ArrayList<TemperatureData> result) {
                contactDetails = result;
                sortContactDetails();
                List<String> data = new ArrayList<String>();

                for (int i = 0; i < result.size(); ++i) {
                    data.add(contactDetails.get(i).getDisplayName());
                }

                display.setData(data);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error fetching contact details");
            }
        });
        */
    }

}

package com.uzh.tempic.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.uzh.tempic.client.presenter.Presenter;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;
import com.uzh.tempic.client.view.WorldDashboardView;
import com.uzh.tempic.client.presenter.WorldMapPresenter;
import com.uzh.tempic.client.view.WorldMapView;
// TODO: ADD SINGLE PRESENTER AND VIEW HERE

public class AppController implements Presenter, ValueChangeHandler<String> {
    private final HandlerManager eventBus;
    private final TempicServiceAsync rpcService;
    private HasWidgets container;

    public AppController(TempicServiceAsync rpcService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);

        // TODO: ADD EVENTHANDLERS TO EVENTBUS
        /*
        eventBus.addHandler(AddContactEvent.TYPE, new AddContactEventHandler() {
            public void onAddContact(AddContactEvent event) {
                doAddNewContact();
            }
        });

        eventBus.addHandler(EditContactEvent.TYPE, new EditContactEventHandler() {
            public void onEditContact(EditContactEvent event) {
                doEditContact(event.getId());
            }
        });

        eventBus.addHandler(EditContactCancelledEvent.TYPE, new EditContactCancelledEventHandler() {
            public void onEditContactCancelled(EditContactCancelledEvent event) {
                doEditContactCancelled();
            }
        });

        eventBus.addHandler(ContactUpdatedEvent.TYPE, new ContactUpdatedEventHandler() {
            public void onContactUpdated(ContactUpdatedEvent event) {
                doContactUpdated();
            }
        });*/
    }


    public void go(final HasWidgets container) {

        this.container = container;

        // If Token is empty create new Item
        if ("".equals(History.getToken())) {
            History.newItem("dashboard");
        }

        else {
            History.fireCurrentHistoryState();
        }

    }

    public void onValueChange(ValueChangeEvent<String> event) {

        String token = event.getValue();

        if (token != null) {
            Presenter presenter = null;

            if (token.equals("dashboard")) {
                presenter = new WorldDashboardPresenter(rpcService, eventBus, new WorldDashboardView());
            }

            // TODO: CHANGE TO SINGLEVIEW WHEN VIEW WAS CREATED (MICHI)
            if (token.equals("country")) {
                presenter = new WorldMapPresenter(rpcService, eventBus, new WorldMapView());
            }

            if (token.equals("worldmap")) {
                presenter = new WorldMapPresenter(rpcService, eventBus, new WorldMapView());
            }

            if (presenter != null) {
                presenter.go(container);
            }
        }

    }

}

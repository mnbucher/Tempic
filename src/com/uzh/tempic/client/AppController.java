package com.uzh.tempic.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.uzh.tempic.client.presenter.Presenter;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;
import com.uzh.tempic.client.view.WorldDashboardView;

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
            if (presenter != null) {
                presenter.go(container);
            }
        }
    }

}

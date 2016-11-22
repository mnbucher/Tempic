package com.uzh.tempic.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.uzh.tempic.client.presenter.CountryPresenter;
import com.uzh.tempic.client.presenter.Presenter;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;
import com.uzh.tempic.client.presenter.WorldMapPresenter;
import com.uzh.tempic.client.view.CountryView;
import com.uzh.tempic.client.view.WorldDashboardView;
import com.uzh.tempic.client.view.WorldMapView;

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
    }


    public void go(final HasWidgets container) {

        this.container = container;

        // If Token is empty set Country as default page
        if ("".equals(History.getToken())) {
            History.newItem("country");
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

            if (token.equals("worldmap")) {
                presenter = new WorldMapPresenter(rpcService, eventBus, new WorldMapView());
            }

            if (token.equals("country")) {
                presenter = new CountryPresenter(rpcService, eventBus, new CountryView());
            }

            if (presenter != null) {
                try {
                    presenter.go(container);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

    }

}

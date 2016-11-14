package com.uzh.tempic.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main Class for our App
 */
public class Tempic implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        TempicServiceAsync rpcService = GWT.create(TempicService.class);
        HandlerManager eventBus = new HandlerManager(null);
        AppController appViewer = new AppController(rpcService, eventBus);
        appViewer.go(RootPanel.get("tempic"));
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}

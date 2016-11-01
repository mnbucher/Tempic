package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TempicServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}

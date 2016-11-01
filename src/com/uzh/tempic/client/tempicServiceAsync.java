package com.uzh.tempic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface tempicServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}

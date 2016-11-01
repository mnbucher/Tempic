package com.uzh.tempic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tempicService")
public interface tempicService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use tempicService.App.getInstance() to access static instance of tempicServiceAsync
     */
    public static class App {
        private static tempicServiceAsync ourInstance = GWT.create(tempicService.class);

        public static synchronized tempicServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

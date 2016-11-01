package com.uzh.tempic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uzh.tempic.client.tempicService;

public class tempicServiceImpl extends RemoteServiceServlet implements tempicService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
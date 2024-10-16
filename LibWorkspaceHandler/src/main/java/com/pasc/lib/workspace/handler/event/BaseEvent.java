package com.pasc.lib.workspace.handler.event;

import java.util.HashMap;

public class BaseEvent {

    private HashMap<String, String> params;

    public HashMap<String, String> getParams() {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        return params;
    }

    public void put(String key, String value) {
        getParams().put(key, value);
    }
}

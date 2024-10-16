package com.pasc.lib.workspace.handler;

import java.util.Map;

public interface IStat {

    public void onEvent(String eventId, Map<String, String> params);

    public void onEvent(String eventId, String label, Map<String, String> params);
}

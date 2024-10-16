package com.pasc.lib.workspace.handler;

import android.app.Activity;

public interface WebProtocolHandler {

    void handle(Activity activity, String url);

    void handle(Activity activity, String url, String title);
}

package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.pasc.lib.workspace.util.AssetUtils;

public class FakeDaoImpl {

    private final Context context;

    protected Context getContext() {
        return context;
    }

    public FakeDaoImpl(Context context) {
        this.context = context;
    }

    public <T> T getFakeData(String name, Class<T> clazz) {
        String string = AssetUtils.getString(context, name);
        return new Gson().fromJson(string, clazz);
    }
}

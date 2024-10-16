package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.pasc.lib.workspace.api.ScrollNewsDao;
import com.pasc.lib.workspace.bean.ScrollNewsResp;
import com.pasc.lib.workspace.util.AssetUtils;

public class FakeScrollNewsDaoImpl extends FakeDaoImpl implements ScrollNewsDao {

    public FakeScrollNewsDaoImpl(Context context) {
        super(context);
    }

    @Override
    public ScrollNewsResp getScrollNews() {
        String string = AssetUtils.getString(getContext(), "fake/getScrollNews.json");
        ScrollNewsResp scrollNewsResp = new Gson().fromJson(string, ScrollNewsResp.class);
        return scrollNewsResp;
    }
}

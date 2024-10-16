package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.util.AssetUtils;

import java.util.ArrayList;
import java.util.List;

public class FakeNewsDaoImpl extends FakeDaoImpl implements NewsDao {

    public FakeNewsDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<InteractionNewsBean> getNews() {
        String string = AssetUtils.getString(getContext(), "fake/getNews.json");
        List<InteractionNewsBean> fakeList = new Gson().fromJson(string, new TypeToken<ArrayList<InteractionNewsBean>>() {
        }.getType());
        return fakeList;
    }
}

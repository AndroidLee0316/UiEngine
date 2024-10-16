package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.workspace.api.AnnouncementApi;
import com.pasc.lib.workspace.api.AnnouncementDao;
import com.pasc.lib.workspace.api.AnnouncementDaoParams;
import com.pasc.lib.workspace.bean.AnnouncementRsp;
import com.pasc.lib.workspace.util.BizUtils;

public class AnnouncementDaoImpl implements AnnouncementDao {

    private final Context context;

    public AnnouncementDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public AnnouncementRsp getAnnouncement(AnnouncementDaoParams params) {

        AnnouncementRsp rsp = BizUtils.getNetData(ApiGenerator.createApi(AnnouncementApi.class).getAnnouncementSync(params));

        return rsp;
    }
}

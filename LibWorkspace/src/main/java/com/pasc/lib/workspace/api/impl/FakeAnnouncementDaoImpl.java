package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.workspace.api.AnnouncementApi;
import com.pasc.lib.workspace.api.AnnouncementDao;
import com.pasc.lib.workspace.api.AnnouncementDaoParams;
import com.pasc.lib.workspace.bean.AnnouncementRsp;
import com.pasc.lib.workspace.util.BizUtils;

public class FakeAnnouncementDaoImpl extends FakeDaoImpl implements AnnouncementDao {

    public FakeAnnouncementDaoImpl(Context context) {
        super(context);
    }

    @Override
    public AnnouncementRsp getAnnouncement(AnnouncementDaoParams params) {
        return getFakeData("fake/getAnnouncement.json", AnnouncementRsp.class);
    }
}

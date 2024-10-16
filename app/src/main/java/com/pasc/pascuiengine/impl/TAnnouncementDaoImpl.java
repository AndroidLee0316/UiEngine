package com.pasc.pascuiengine.impl;

import android.content.Context;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.workspace.api.AnnouncementApi;
import com.pasc.lib.workspace.api.AnnouncementDao;
import com.pasc.lib.workspace.api.AnnouncementDaoParams;
import com.pasc.lib.workspace.bean.Announcement;
import com.pasc.lib.workspace.bean.AnnouncementRsp;
import com.pasc.lib.workspace.util.BizUtils;

import java.util.List;

public class TAnnouncementDaoImpl implements AnnouncementDao {

    private final Context context;

    public TAnnouncementDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public AnnouncementRsp getAnnouncement(AnnouncementDaoParams params) {
        AnnouncementRsp rsp = (AnnouncementRsp) BizUtils.getNetData(((AnnouncementApi) ApiGenerator.createApi(AnnouncementApi.class)).getAnnouncementSync(params));
        if (rsp != null) {
            List<Announcement> announcementList = rsp.getAnnouncementList();
            if (announcementList != null) {
                for (Announcement announcement : announcementList) {
                    announcement.setSkipUrl("https://smt-stg.yun.city.pingan.com/basic/stg/app/feature/message/?uiparams=%7B%22title%22:%22%22%7D#/announcement/" + announcement.getId());
                }
            }
        }
        return rsp;
    }
}

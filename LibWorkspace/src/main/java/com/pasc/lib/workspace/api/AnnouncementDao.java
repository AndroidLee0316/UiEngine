package com.pasc.lib.workspace.api;

import com.pasc.lib.workspace.bean.AnnouncementRsp;
import com.pasc.lib.workspace.bean.AppBannerRsp;

public interface AnnouncementDao {
    AnnouncementRsp getAnnouncement(AnnouncementDaoParams params);
}

package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementRsp {

    @SerializedName("announcementList")
    private List<Announcement> announcementList;

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }
}
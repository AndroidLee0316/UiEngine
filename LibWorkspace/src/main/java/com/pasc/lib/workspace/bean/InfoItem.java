package com.pasc.lib.workspace.bean;

import java.util.HashMap;

/**
 * 信息项类.
 */
public class InfoItem {

    private String title;
    private String desc;
    private InfoItemType type;
    private String onClick;
    private HashMap<String, String> onClickParams;
    private String[] imgUrls;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public InfoItemType getType() {
        return type;
    }

    public void setType(InfoItemType type) {
        this.type = type;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public HashMap<String, String> getOnClickParams() {
        return onClickParams;
    }

    public void setOnClickParams(HashMap<String, String> onClickParams) {
        this.onClickParams = onClickParams;
    }

    public String[] getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String[] imgUrls) {
        this.imgUrls = imgUrls;
    }
}

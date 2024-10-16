package com.pasc.business.workspace.widget.event;

import com.pasc.lib.workspace.bean.BannerBean;

public class BannerItemClickEvent {

    private int position;
    private BannerBean bannerBean;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
    }

    public BannerBean getBannerBean() {
        return bannerBean;
    }
}

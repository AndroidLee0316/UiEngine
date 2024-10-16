package com.pasc.lib.workspace.api;

import com.pasc.lib.workspace.bean.AppBannerRsp;

public interface BannerDao {
    AppBannerRsp getBanner(BannerDaoParams params);
}

package com.pasc.lib.workspace.api;

import com.pasc.lib.workspace.bean.AppBannerRsp;
import com.pasc.lib.workspace.bean.ConfigResp;

public interface ConfigDao {

    ConfigResp getConfig(ConfigDaoParams params);
}

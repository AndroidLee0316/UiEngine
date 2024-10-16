package com.pasc.business.life.router.location;

import android.app.Activity;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by ex-huangzhiyi001 on 18/10/22.
 */
public interface ILocation extends IProvider {
    /**
     * 获取定位信息
     * @return
     */
    void getLocation(Activity activity, Callback callback);

}

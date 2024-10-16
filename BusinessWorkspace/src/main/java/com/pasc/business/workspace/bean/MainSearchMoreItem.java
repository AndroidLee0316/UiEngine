package com.pasc.business.workspace.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pasc.business.workspace.Constants;

/**
 * Created by chendaixi947 on 2018/6/12
 *
 * @since 1.0
 */
public class MainSearchMoreItem implements MultiItemEntity {

    public String title;
    public int type;// 0 :更多服务  1：更多事项  2：更多资讯

    public MainSearchMoreItem(String title,int type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public int getItemType() {
        return Constants.TYPE_10;
    }
}

package com.pasc.business.workspace.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pasc.business.workspace.Constants;

/**
 * Created by chendaixi947 on 2018/6/12
 *
 * @since 1.0
 */
public class MainSearchTitleItem implements MultiItemEntity {

    public String title;

    public MainSearchTitleItem(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return Constants.TYPE_06;
    }
}

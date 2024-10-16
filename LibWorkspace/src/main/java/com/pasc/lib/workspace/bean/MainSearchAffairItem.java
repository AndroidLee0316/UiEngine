package com.pasc.lib.workspace.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zc  2018年08月08日19:49:33
 */

public class MainSearchAffairItem implements MultiItemEntity {



    @SerializedName("affarisName")
    public String affarisName;//政务名称

    @SerializedName("orgName")
    public String orgName;//部门名称

    @SerializedName("affarisId")
    public String affarisId;//政务ID,

    @SerializedName("h5LinkURL")
    public String h5LinkURL;//:wwww.baidu.com"

    @SerializedName("ifOnlineSb")
    public String ifOnlineSb;//是否可以在线办理

    @SerializedName("associationalWord")
    public String associationalWord;//关联词


    //1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯 ；5：大图类型;6:服务搜索结果标题模块；7：服务模块;8:分割线 9 事项 10 更多
    @Override
    public int getItemType() {
       return 9;
    }
}

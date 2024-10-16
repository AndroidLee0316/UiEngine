package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import java.util.List;

public class ScrollNewsResp {
    @SerializedName("newsList")
    public List<InteractionNewsBean> scrollNews;
}

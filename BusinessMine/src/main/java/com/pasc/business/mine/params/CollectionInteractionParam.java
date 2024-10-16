package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.net.BaseTokenParam;

/**
 * 政民互动
 */
public class CollectionInteractionParam extends BaseTokenParam {
    @SerializedName("type")
    public String type;//"type": " 3",//政民互动类型，今日南通 3,时政 4,社会 5,民生 6,国内 7,该字段为空或者传，返回全部数据
    @SerializedName("offSet")
    public String offSet;//起始位置
    @SerializedName("pageSize")
    public String pageSize;//每页的条数，默认10条

    public CollectionInteractionParam(String type, String offSet, String pageSize) {
        this.type = type;
        this.offSet = offSet;
        this.pageSize = pageSize;
    }
}

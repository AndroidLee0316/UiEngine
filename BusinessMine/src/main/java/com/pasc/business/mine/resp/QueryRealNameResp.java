package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;


/**
 * 查询实名认证方式 以及是否绑定了社保 by zc 2018年08月04日10:33:47
 */
public class QueryRealNameResp {
    // "1(1银行卡实名认证;2,人脸实名认证)"
    @SerializedName("certificationType")
    public int certificationType;
    //用户名
    @SerializedName("userName")
    public String userName;
    //身份证
    @SerializedName("idCard")
    public String idCard;
    //是否绑定了社保
    @SerializedName("socialIsBind")
    public boolean socialIsBind;
    //是否需要校验人脸
    @SerializedName("isCheckAgain")
    public boolean isNeedCheckFace;


}

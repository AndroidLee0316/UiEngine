package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chendaixi947 on 2018/7/7
 *
 * @since 1.0
 */
//@Table(database = WorkspaceDb.class)
//public class AdvertisementResp extends BaseModel {
public class AdvertisementResp {
    //    @Column(name = "id")
//    @PrimaryKey(autoincrement = true)
    public long id;

    /**
     * 版本号
     */
//    @Column(name = "version")
    @SerializedName("version")
    public String version;

    @SerializedName("projectileInfo")
    public AdvertisementBean advertisementBean;

}

package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 住房保障
 * Created by chendaixi947 on 2018/5/7
 *
 * @since 1.0
 */
//@Table(database = WorkspaceDb.class)
//public class HouseSecurityResp extends BaseModel {
public class HouseSecurityResp {
    public static final int STATUS_UP = 1;//上升
    public static final int STATUS_DOWN = -1;//下降
    public static final int STATUS_FLAT = 0;//不变

    //    @Column(name = "id")
//    @PrimaryKey(autoincrement = true)
    public int id;
    //    @Column(name = "saleAmount")
    @SerializedName("saleAmount")
    public String saleAmount;//在售楼盘数
    //    @Column(name = "averagePrice")
    @SerializedName("averagePrice")
    public String averagePrice;//均价
    //    @Column(name = "linkRelativeRatio")
    @SerializedName("linkRelativeRatio")
    public String linkRelativeRatio;//环比
    //    @Column(name = "fallingNumber")
    @SerializedName("fallingNumber")
    public String fallingNumber;//均价下跌板块数
    //    @Column(name = "risingNumber")
    @SerializedName("risingNumber")
    public String risingNumber;//均价上涨板块数
    //    @Column(name = "status")
    @SerializedName("status")
    public int status = 1;//环比状态(1:上升，0：不变，-1：下降)

    @Override
    public String toString() {
        return "HouseSecurityResp{"
                + "id="
                + id
                + ", saleAmount="
                + saleAmount
                + ", averagePrice="
                + averagePrice
                + ", linkRelativeRatio="
                + linkRelativeRatio
                + ", fallingNumber="
                + fallingNumber
                + ", risingNumber="
                + risingNumber
                + '}';
    }
}

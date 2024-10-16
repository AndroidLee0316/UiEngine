package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 今日水质bean类
 * Created by chendaixi947 on 2018/5/7
 *
 * @since 1.0
 */

public class TodayWaterQualityItem {


    @SerializedName("waterQualityId")
    public String waterQualityId;//水质数据id
    @SerializedName("title")
    public String title;//标题,如"崇海水厂出厂水质公示（2018.4.25）"
    @SerializedName("name")
    public String name;//水厂名称，如：崇海水厂
    @SerializedName("link")
    public String link;//链接
    @SerializedName("issueDate")
    public String issueDate;//发布时间
    @SerializedName("turbidity")
    public String turbidity;//浑浊度
    @SerializedName("information_link_h5")
    public String informationLinkH5;//H5链接

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodayWaterQualityItem that = (TodayWaterQualityItem) o;

        if (waterQualityId != null ? !waterQualityId.equals(that.waterQualityId) : that.waterQualityId != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (issueDate != null ? !issueDate.equals(that.issueDate) : that.issueDate != null)
            return false;
        if (turbidity != null ? !turbidity.equals(that.turbidity) : that.turbidity != null)
            return false;
        return informationLinkH5 != null ? informationLinkH5.equals(that.informationLinkH5) : that.informationLinkH5 == null;
    }

    @Override
    public int hashCode() {
        int result = waterQualityId != null ? waterQualityId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (issueDate != null ? issueDate.hashCode() : 0);
        result = 31 * result + (turbidity != null ? turbidity.hashCode() : 0);
        result = 31 * result + (informationLinkH5 != null ? informationLinkH5.hashCode() : 0);
        return result;
    }
}

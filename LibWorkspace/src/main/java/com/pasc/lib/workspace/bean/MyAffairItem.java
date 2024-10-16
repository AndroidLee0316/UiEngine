package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

public class MyAffairItem {
    public static final int TYPE_PENDING_COMMIT = 0;// 待提交--继续编辑
    public static final int TYPE_WAIT_AUDIT = 1;    // 审核中--查看详情
    public static final int TYPE_FINISHED = 2;      // 已完成--查看详情
    public static final int TYPE_BACK = 3;          // 已驳回--查看详情、重新申报

    @SerializedName("no")
    public String no;//详情查询使用
    @SerializedName("endTime")
    public String endTime;//当前所处流程节点结束时间
    @SerializedName("bjStatu")
    public String biStatu;//申请状态 (四种状态：在线申办，收件/受理 办结  办理中)
    @SerializedName("eventName")
    public String eventName;//当前所处的流程节点
    @SerializedName("note")
    public String note;//审批意见
    @SerializedName("applyDate")
    public String applyDate;//申办时间
    @SerializedName("deptYwName")
    public String deptYwName;//办理业务名称
    @SerializedName("statuNo")
    public int statuNo;// 状态值："0暂停,1审核中,2已完成,3已驳回"
    @SerializedName("type")
    public int type;// 状态值："1政务审批,2公安局三超证审批"
    @SerializedName("serviceId")
    public String serviceId;// 业务id
    @SerializedName("h5Link")
    public String h5Link;// 跳转链接

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyAffairItem)) return false;

        MyAffairItem that = (MyAffairItem) o;

        if (statuNo != that.statuNo) return false;
        if (type != that.type) return false;
        if (no != null ? !no.equals(that.no) : that.no != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (biStatu != null ? !biStatu.equals(that.biStatu) : that.biStatu != null) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null)
            return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null)
            return false;
        if (deptYwName != null ? !deptYwName.equals(that.deptYwName) : that.deptYwName != null)
            return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null)
            return false;
        return h5Link != null ? h5Link.equals(that.h5Link) : that.h5Link == null;
    }

    @Override
    public int hashCode() {
        int result = no.hashCode();
        result = 31 * result + endTime.hashCode();
        result = 31 * result + biStatu.hashCode();
        result = 31 * result + eventName.hashCode();
        result = 31 * result + note.hashCode();
        result = 31 * result + applyDate.hashCode();
        result = 31 * result + deptYwName.hashCode();
        result = 31 * result + statuNo;
        result = 31 * result + type;
        result = 31 * result + serviceId.hashCode();
        result = 31 * result + h5Link.hashCode();
        return result;
    }
}

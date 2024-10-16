package com.pasc.business.affair.db;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/6
 */
@Table(database = UserDb.class)
public class NewsInfo extends BaseModel implements MultiItemEntity{
    public static final int TYPE_ITEM = 1;
    @SerializedName("id")
    @Column(name = "id")
    @PrimaryKey(autoincrement = true)
    public int id ;
    @SerializedName("sessionId")
    public String sessionId;
    @SerializedName("token")
    public String token;
    @SerializedName("remark")
    public String remark;
    @SerializedName("title")
    @Column(name = "title")
    public String title;
    @SerializedName("publishUnit")
    public String publishUnit;
    @SerializedName("infoTime")
    public String infoTime;
    @SerializedName("content")
    public String content;
    @SerializedName("clickCount")
    public int clickCount;

    @Override
    public int getItemType() {
        return TYPE_ITEM;
    }
}

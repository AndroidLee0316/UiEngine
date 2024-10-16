package com.pasc.business.affair.db;

import com.pasc.business.affair.resp.SearchAffairInfoResp;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/5
 */
@Table(database = UserDb.class)
public class SearchInfo extends BaseModel{
//    public SearchInfo(){}
//
//    /**
//     t* @param name
//     */
//    public SearchInfo(String name,int typeitem){
//        this.name = name;
//        this.typeitem =typeitem;
//    }
//    public SearchInfo(String name,String source,String picURL,String link){
//        this.name = name;
//        this.picURL =picURL;
//        this.source = source;
//        this.link = link;
//    }
    @Column(name = "id")
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column(name = "picURL")
    public String picURL;
    @Column(name = "name")
    public String name;
    @Column(name = "link")
    public String link;
    @Column(name = "source")
    public String source;
    @Column(name = "queryLink")
    public String queryLink;
    @Column(name = "type")
    public String type;
    @Column(name = "orderLink")
    public String orderLink;
    @Column(name = "affarisId")
    public String affarisId;
    @Column(name = "h5LinkURL")
    public String h5LinkURL;
    @Column(name = "affarisName")
    public String affarisName;
    @Column(name = "ifOnlineSb")
    public String ifOnlineSb;
    @Column(name = "typeitem")
    public int typeitem = SearchAffairInfoResp.TYPE_ICON_TWOTEXT;

    public String affarisType;
    public String title;
}

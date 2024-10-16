package com.pasc.business.affair.params;

import com.google.gson.annotations.SerializedName;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/3
 */
public class SearchAffairPermars {


    /**
     * @param keyword
     */
    public SearchAffairPermars(String keyword , String type) {
      this.keyword = keyword;
      this.type = type;
    }
    public SearchAffairPermars(String type , int offSet,int pageSize) {
        this.type = type;
        this.offSet = offSet;
        this.pageSize = pageSize;
    }
    //    "queryText":"南通",//搜索词
//            "offSet":"0",
//            "pageSize":"2"
    @SerializedName("keyword")
    public String keyword;
    @SerializedName("type")
    public String type = "0";
    @SerializedName("offSet")
    public int offSet = 0;
    @SerializedName("pageSize")
    public int pageSize= 10;
}

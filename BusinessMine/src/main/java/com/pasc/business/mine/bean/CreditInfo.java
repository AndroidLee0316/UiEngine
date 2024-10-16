package com.pasc.business.mine.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ex-wuhaiping001 on 17/12/18.
 */

public class CreditInfo implements Serializable {
  @SerializedName("dataBuildTime")
  public String dataBuildTime;
  @SerializedName("credooScore")
  public String credooScore;
  @SerializedName("credooScoreDesc")
  public String credooScoreDesc;
  @SerializedName("credooScoreMax")
  public String credooScoreMax;
}

package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;

public class ModifyUserParams {
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("sex")
    public int sex;

  public ModifyUserParams(String nickName, int sex) {
    this.nickName = nickName;
    this.sex = sex;
  }
}

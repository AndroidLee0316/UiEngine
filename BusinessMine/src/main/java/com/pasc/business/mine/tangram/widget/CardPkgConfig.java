package com.pasc.business.mine.tangram.widget;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/11/9
 * 卡包样式配置
 */
public class CardPkgConfig {

    @SerializedName("hGap")
    public int hGap;
    @SerializedName("scrollMarginLeft")
    public int scrollMarginLeft;
    @SerializedName("scrollMarginRight")
    public int scrollMarginRight;
    @SerializedName("hasIndicator")
    public boolean hasIndicator = false;

}

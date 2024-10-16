package com.pasc.business.mine.tangram.bean;

import android.support.annotation.IntDef;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/12/8
 * 卡包信息
 */
public interface ICardPkgBean {
    /**
     * 0x1：模板卡包样式；0x2：江北卡包样式 ；0x4：江北卡包添加；0x8：加班卡包banner ；
     */
    int TYPE_CARD_BASE = 0x1;
    int TYPE_CARD_JB = 0x2;
    int TYPE_CARD_JB_ADD = 0x4;
    int TYPE_CARD_JB_BANNER = 0x8;

    /**
     * 卡的名字，如：公积金，xxx公司
     *
     * @return 卡的名字
     */
    String getCardName();

    /**
     * 卡的机构，如：南通市公积金管理中心，公司名称
     *
     * @return 卡的机构
     */
    String getCardDept();

    /**
     * 卡包描述，如：电子凭证、违法在线办理，"22x********231","办理中"
     *
     * @return 卡的描述
     */
    String getCardDesc();

    /**
     * 卡包背景（网络），如："https://www.baidu.com/img/bd_logo1.png"
     *
     * @return 卡的背景图片url
     */
    String getCardBgUrl();

    /**
     * 卡包背景（本地），如："https://www.baidu.com/img/bd_logo1.png"
     *
     * @return 卡的背景
     */
    int getCardBgRes();

    /**
     * 卡包绑定按钮行为描述（本地），如："去登陆"，"去绑定"，没有则传""
     *
     * @return 绑定按钮行为描述
     */
    String getCardActionDesc();

    /**
     * 返回卡包类型
     *
     * @return 卡的类型
     */
    @CardType
    int getCardType();

    @IntDef({TYPE_CARD_BASE, TYPE_CARD_JB, TYPE_CARD_JB_ADD, TYPE_CARD_JB_BANNER})
    @interface CardType {
    }

}

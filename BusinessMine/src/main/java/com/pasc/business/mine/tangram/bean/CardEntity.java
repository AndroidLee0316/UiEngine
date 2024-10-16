package com.pasc.business.mine.tangram.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.pasc.business.mine.tangram.bean.ICardPkgBean.TYPE_CARD_BASE;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/12/8
 */
public class CardEntity implements MultiItemEntity {
    private int type = TYPE_CARD_BASE;
    private ICardPkgBean iCardPkgBean;

    public CardEntity(int type, ICardPkgBean iCardPkgBean) {
        this.type = type;
        this.iCardPkgBean = iCardPkgBean;
    }

    public ICardPkgBean getCardPkgBean() {
        return iCardPkgBean;
    }

    @Override
    public int getItemType() {
        return type;
    }
}

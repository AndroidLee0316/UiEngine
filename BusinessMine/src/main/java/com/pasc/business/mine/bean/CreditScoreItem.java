package com.pasc.business.mine.bean;

/**
 * Created by xiongjiaping112 on 17/12/15.
 */

public class CreditScoreItem {
    public final int icon;
    public final String name;
    public final String score;
    public final String desc;

    public CreditScoreItem(int icon, String name, String score, String desc) {
        this.icon = icon;
        this.name = name;
        this.score = score;
        this.desc = desc;
    }
}

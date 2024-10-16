package com.pasc.business.mine.bean;

/**
 * Created by ruanwei489 on 2018/1/23.
 */

public class BusinessItem {
    public String title;
    public String time;

    public static BusinessItem create(String title, String time) {
        BusinessItem item = new BusinessItem();
        item.title = title;
        item.time = time;
        return item;
    }
}

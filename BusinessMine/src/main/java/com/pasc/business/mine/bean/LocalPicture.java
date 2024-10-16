package com.pasc.business.mine.bean;

/**
 * Created by ex-lingchun001 on 2018/3/6.
 */

public class LocalPicture {
    public String path;
    public boolean select = false;

    public LocalPicture(String path) {
        this.path = path;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

}

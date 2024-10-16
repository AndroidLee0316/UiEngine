package com.pasc.lib.workspace.handler.event;

public class GoToInteractionEvent<Data> {
    public static final int NT_NEWS = 1; //今日南通

    private int type;//类型，1：今日南通，2：民生资讯
    private Data data;//携带的数据

    public GoToInteractionEvent(int type) {
        this.type = type;
    }

    public GoToInteractionEvent(int type, Data data) {
        this.type = type;
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public int getType() {
        return type;
    }

}

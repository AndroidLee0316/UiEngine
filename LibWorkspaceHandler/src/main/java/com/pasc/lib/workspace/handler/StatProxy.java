package com.pasc.lib.workspace.handler;

import java.util.Map;

public class StatProxy implements IStat {

    private static final StatProxy ourInstance = new StatProxy();

    private IStat stat;

    public static StatProxy getInstance() {
        return ourInstance;
    }

    public void init(IStat stat) {
        this.stat = stat;
    }

    private StatProxy() {
    }

    @Override
    public void onEvent(String eventId, Map<String, String> params) {
        if (stat != null) {
            stat.onEvent(eventId, params);
        } else {
            System.err.println("请先初始化统计代理");
        }
    }

    @Override
    public void onEvent(String eventId, String label, Map<String, String> params) {
        if (stat != null) {
            stat.onEvent(eventId, label, params);
        } else {
            System.err.println("请先初始化统计代理");
        }
    }
}

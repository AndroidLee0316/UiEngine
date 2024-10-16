package com.pasc.lib.workspace.handler;

import android.view.View;

import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONObject;

/**
 * 点击参数.
 */
public class TangramClickParams {

    private String clickUrl;
    private JSONObject clickParams;
    private View targetView;
    private BaseCell cell;
    private int eventType;

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public JSONObject getClickParams() {
        return clickParams;
    }

    public void setClickParams(JSONObject clickParams) {
        this.clickParams = clickParams;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    public BaseCell getCell() {
        return cell;
    }

    public void setCell(BaseCell cell) {
        this.cell = cell;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}

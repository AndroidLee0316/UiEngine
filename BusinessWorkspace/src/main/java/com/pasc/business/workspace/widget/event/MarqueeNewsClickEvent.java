package com.pasc.business.workspace.widget.event;

import android.view.View;

import com.pasc.business.workspace.widget.MarqueeNewsView;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

import java.util.List;

public class MarqueeNewsClickEvent {

    private MarqueeNewsView marqueeNewsView;
    private View clickView;
    private int clickPosition;
    private List<InteractionNewsBean> dataSource;

    public void setMarqueeNewsView(MarqueeNewsView marqueeNewsView) {
        this.marqueeNewsView = marqueeNewsView;
    }

    public MarqueeNewsView getMarqueeNewsView() {
        return marqueeNewsView;
    }

    public void setClickView(View clickView) {
        this.clickView = clickView;
    }

    public View getClickView() {
        return clickView;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    public int getClickPosition() {
        return clickPosition;
    }

    public void setDataSource(List<InteractionNewsBean> dataSource) {
        this.dataSource = dataSource;
    }

    public List<InteractionNewsBean> getDataSource() {
        return dataSource;
    }
}

package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class InfoItemView extends BaseCardView {

    private TextView titleView;
    private TextView descView;
    private TextView timeView;

    private RoundedImageView singleImgView;

    private ViewGroup threeImgContainer;
    private RoundedImageView centerImgView1;
    private RoundedImageView centerImgView2;
    private RoundedImageView centerImgView3;
    private RoundedImageView[] threeImgViews;

    public InfoItemView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_info_item_view, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
        }

        titleView = getViewById(R.id.titleView);
        timeView = getViewById(R.id.timeView);
        descView = getViewById(R.id.descView);
        singleImgView = getViewById(R.id.rightImgView);
        threeImgContainer = getViewById(R.id.threeImgContainer);
        centerImgView1 = getViewById(R.id.centerImgView1);
        centerImgView2 = getViewById(R.id.centerImgView2);
        centerImgView3 = getViewById(R.id.centerImgView3);
        threeImgViews = new RoundedImageView[]{centerImgView1, centerImgView2, centerImgView3};
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getTimeView() {
        return timeView;
    }

    public TextView getDescView() {
        return descView;
    }

    public RoundedImageView getSingleImgView() {
        return singleImgView;
    }

    public RoundedImageView[] getThreeImgViews() {
        return threeImgViews;
    }

    public void setState(State state) {
        switch (state) {
            case NO_IMG: {
                singleImgView.setVisibility(View.GONE);
                threeImgContainer.setVisibility(View.GONE);
                break;
            }
            case THREE_IMG: {
                singleImgView.setVisibility(View.GONE);
                threeImgContainer.setVisibility(View.VISIBLE);
                break;
            }
            case SINGLE_IMG: {
                singleImgView.setVisibility(View.VISIBLE);
                threeImgContainer.setVisibility(View.GONE);
                break;
            }
            default:
                setState(State.NO_IMG);
                break;
        }
    }

    public enum State {
        NO_IMG // 无图
        , SINGLE_IMG // 单图
        , THREE_IMG // 三图
    }
}

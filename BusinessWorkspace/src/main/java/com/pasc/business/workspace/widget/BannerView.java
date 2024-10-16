package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.lib.widget.banner.SliderLayout;
import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardView;

public class BannerView extends BaseCardView {

    // 上下文
    Context context;

    // 广告栏布局
    SliderLayout sliderLayout;

    // 遮盖物布局
    View shelterLayout;
    ImageView shelterImage;

    // 图框
    View imgFrameLayout;

    // 操作栏布局
    View actionLayout;
    ImageView icon0;
    TextView title0;
    ImageView icon1;
    TextView title1;
    ImageView icon2;
    TextView title2;
    ImageView icon3;
    TextView title3;
    View action0;
    View action1;
    View action2;
    View action3;

    public BannerView(@NonNull Context mContext) {
        super(mContext);
        context = mContext;
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_banner_view, this);

        sliderLayout = findViewById(R.id.slider);

        shelterLayout = findViewById(R.id.shelterLayout);
        shelterImage = getViewById(R.id.shelterImage);

        imgFrameLayout = getViewById(R.id.imgFrameLayout);

        actionLayout = findViewById(R.id.actionLayout);

        action0 = getViewById(R.id.action0);
        action1 = getViewById(R.id.action1);
        action2 = getViewById(R.id.action2);
        action3 = getViewById(R.id.action3);
        icon0 = getViewById(R.id.icon0);
        title0 = getViewById(R.id.title0);
        icon1 = getViewById(R.id.icon1);
        title1 = getViewById(R.id.title1);
        icon2 = getViewById(R.id.icon2);
        title2 = getViewById(R.id.title2);
        icon3 = getViewById(R.id.icon3);
        title3 = getViewById(R.id.title3);
    }

    public void setShelter(String shelter) {
        if (shelterImage != null) {
            int res = getShelterRes(shelter);
            shelterImage.setImageResource(res);
        }
    }

    private int getShelterRes(String shelter) {
        if ("sphereSurface".equals(shelter)) {
            return R.drawable.workspace_banner_shelter_sphere_surface;
        } else if ("concave".equals(shelter)) {
            return R.drawable.workspace_banner_shelter_concave;
        }
        return R.drawable.workspace_banner_shelter_sphere_surface;
    }

    public void setShelterVisible(boolean shelterVisible) {
        if (shelterLayout != null) {
            if (shelterVisible) {
                shelterLayout.setVisibility(View.VISIBLE);
            } else {
                shelterLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setImgFrameVisible(boolean imgFrameVisible) {
        if (imgFrameLayout != null) {
            if (imgFrameVisible) {
                imgFrameLayout.setVisibility(View.VISIBLE);
            } else {
                imgFrameLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setActionsVisible(boolean actionsVisible) {
        if (actionLayout != null) {
            if (actionsVisible) {
                actionLayout.setVisibility(View.VISIBLE);
            } else {
                actionLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setDefaultImgRes(int imgDefaultRes) {
        if (sliderLayout != null) {
            sliderLayout.setDefaultImg(imgDefaultRes);
        }
    }

    public void setImgFrame(String imgFrame) {
        if (imgFrameLayout != null) {
            int res = getImgFrameRes(imgFrame);
            imgFrameLayout.setBackgroundResource(res);
        }
    }

    protected int getImgFrameRes(String imgFrame) {
        if ("radius".equals(imgFrame)) {
            return R.drawable.workspace_img_radius_photo_frame;
        } else if("concave".equals(imgFrame)){
            return R.drawable.workspace_img_concave_photo_frame;
        }
        return R.drawable.workspace_img_radius_photo_frame;
    }
}

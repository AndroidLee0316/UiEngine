package com.pasc.business.workspace.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardView;
import com.pasc.lib.workspace.bean.TodayWaterQualityItem;
import com.pasc.lib.workspace.handler.HandlerProxy;
import com.pasc.lib.workspace.handler.StatProxy;

import java.util.HashMap;
import java.util.Map;


public class DataBoardWaterView extends BaseCardView {

    TextView tvTodayWaterQuality;
    //狼山水厂浑浊度
    TextView tvWolfLandscapesQuality;
    //崇海水厂浑浊度
    TextView tvChongHaiWaterQuality;
    //洪港水厂浑浊度
    TextView tvHongGangWaterQuality;
    TextView tvHongGangWaterQualityName;
    TextView tvWolfLandscapesQualityName;
    TextView tvChongHaiWaterQualityName;

    public DataBoardWaterView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_item_main_today_water_quality, this);

        tvTodayWaterQuality = getViewById(R.id.tv_today_water_quality);
        tvWolfLandscapesQuality = getViewById(R.id.tv_wolf_landscape_factory);
        tvChongHaiWaterQuality = getViewById(R.id.tv_chong_hai_water_plant);
        tvHongGangWaterQuality = getViewById(R.id.tv_hong_gang_water_quality);
        tvHongGangWaterQualityName = getViewById(R.id.tv_hong_gang_water_quality_name);
        tvWolfLandscapesQualityName = getViewById(R.id.tv_wolf_landscape_factory_name);
        tvChongHaiWaterQualityName = getViewById(R.id.tv_chong_hai_water_plant_name);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    TodayWaterQualityItem item = (TodayWaterQualityItem) v.getTag();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("url", item.informationLinkH5);

                    if (context instanceof Activity) {
                        HandlerProxy.getInstance().getProtocolHandler().handle((Activity) context, item.informationLinkH5, null);
                    }

                    //埋点
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("服务名称", "公共服务--" + item.name);
                    StatProxy.getInstance().onEvent("home", hashMap);
                }
            }
        };
        tvWolfLandscapesQuality.setOnClickListener(onClickListener);
        tvChongHaiWaterQuality.setOnClickListener(onClickListener);
        tvHongGangWaterQuality.setOnClickListener(onClickListener);
        tvHongGangWaterQualityName.setOnClickListener(onClickListener);
        tvWolfLandscapesQualityName.setOnClickListener(onClickListener);
        tvChongHaiWaterQualityName.setOnClickListener(onClickListener);
    }
}

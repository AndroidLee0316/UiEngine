package com.pasc.business.workspace.widget;

import android.support.annotation.NonNull;

import com.pasc.lib.base.util.CollectionUtils;
import com.pasc.lib.widget.tangram.BaseCardCell;
import com.pasc.lib.workspace.bean.TodayWaterQualityItem;
import com.pasc.lib.workspace.bean.TodayWaterQualityResp;

import java.util.List;

public class DataBoardWaterCell extends BaseCardCell<DataBoardWaterView> {

    private static final String TAG = "DataBoardWaterCell";

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    protected Class getDataSourceType() {
        return TodayWaterQualityResp.class;
    }

    @Override
    public void postBindView(@NonNull DataBoardWaterView view) {
        super.postBindView(view);
    }

    @Override
    protected void bindViewData(@NonNull DataBoardWaterView view) {
        super.bindViewData(view);

        List dataSource = getListDataSource();
        if (CollectionUtils.isNotEmpty(dataSource)) {
            Object obj = dataSource.get(0);
            if (obj != null && obj instanceof TodayWaterQualityResp) {
                TodayWaterQualityResp todayWaterQualityResp = (TodayWaterQualityResp) obj;
                if (todayWaterQualityResp != null) {
                    for (TodayWaterQualityItem item : todayWaterQualityResp.waterQualityInfoList) {
                        Object chongHaiWaterQualityTag = view.tvChongHaiWaterQuality.getTag();
                        Object wolfLandscapesQualityTag = view.tvWolfLandscapesQuality.getTag();
                        Object hongGangWaterQualityTag = view.tvHongGangWaterQuality.getTag();

                        if (item.name.contains("崇海") && chongHaiWaterQualityTag == null) {
                            view.tvChongHaiWaterQuality.setText(item.turbidity);
                            view.tvChongHaiWaterQuality.setTag(item);
                            view.tvChongHaiWaterQualityName.setTag(item);
                        } else if (item.name.contains("狼山") && wolfLandscapesQualityTag == null) {
                            view.tvWolfLandscapesQuality.setText(item.turbidity);
                            view.tvWolfLandscapesQuality.setTag(item);
                            view.tvWolfLandscapesQualityName.setTag(item);
                        } else if (item.name.contains("洪港") && hongGangWaterQualityTag == null) {
                            view.tvHongGangWaterQuality.setText(item.turbidity);
                            view.tvHongGangWaterQuality.setTag(item);
                            view.tvHongGangWaterQualityName.setTag(item);
                        }

                        if (chongHaiWaterQualityTag != null
                                && wolfLandscapesQualityTag != null
                                && hongGangWaterQualityTag != null) {
                            break;
                        }

                        view.tvTodayWaterQuality.setText(todayWaterQualityResp.quality);
                    }
                }
            }
        }
    }
}

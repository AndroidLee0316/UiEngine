package com.pasc.business.workspace.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardCell;
import com.pasc.lib.workspace.bean.HouseSecurityResp;

import java.util.List;

public class DataBoardHouseCell extends BaseCardCell<DataBoardHouseView> {

    private static final String TAG = "DataBoardHouseCell";

    @Override
    protected Class getDataSourceType() {
        return HouseSecurityResp.class;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public void postBindView(@NonNull DataBoardHouseView view) {
        super.postBindView(view);
    }

    @Override
    protected void bindViewData(@NonNull DataBoardHouseView view) {
        super.bindViewData(view);
        if (view == null) return;

        List dataSource = getListDataSource();
        if (dataSource != null && dataSource.size() > 0) {
            HouseSecurityResp houseSecurityResp = (HouseSecurityResp) dataSource.get(0);
            if (houseSecurityResp != null) {
                view.tvHouseNewNum.setText(houseSecurityResp.saleAmount);
                view.tvHousePrice.setText(houseSecurityResp.averagePrice);
                view.tvHousePriceDownNum.setText(houseSecurityResp.fallingNumber);
                view.tvHousePriceUpNum.setText(houseSecurityResp.risingNumber);
                view.tvHousePriceTrend.setText(houseSecurityResp.linkRelativeRatio);
                switch (houseSecurityResp.status) {
                    case HouseSecurityResp.STATUS_DOWN://下降
                        view.ivHousePriceTrend.setImageResource(R.drawable.workspace_ic_main_page_house_price_trend_down);
                        view.tvHousePriceTrend.setTextColor(Color.parseColor("#8ace55"));
                        break;
                    case HouseSecurityResp.STATUS_UP://上升
                        view.ivHousePriceTrend.setImageResource(R.drawable.workspace_ic_main_page_house_price_trend_up);
                        view.tvHousePriceTrend.setTextColor(Color.parseColor("#ec5b56"));
                        break;
                    case HouseSecurityResp.STATUS_FLAT://不变
                        Context context = view.tvHousePrice.getContext();
                        view.tvHousePriceTrend.setTextColor(Color.parseColor("#333333"));
                        view.ivHousePriceTrend.setImageResource(R.drawable.workspace_ic_main_page_house_price_flat);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

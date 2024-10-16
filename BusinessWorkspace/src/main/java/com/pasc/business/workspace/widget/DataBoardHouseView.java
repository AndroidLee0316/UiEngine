package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardView;

public class DataBoardHouseView extends BaseCardView {

    public ImageView ivHousePriceTrend;
    public TextView tvHousePriceDownNum;
    public TextView tvHousePriceUpNum;
    public TextView tvHouseNewNum;
    public TextView tvHousePriceTrend;
    public TextView tvHousePrice;

    public DataBoardHouseView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_item_main_house_security, this);

        //房子均价
        tvHousePrice = getViewById(R.id.tv_house_price_per_square_meter);
        //30天环比
        tvHousePriceTrend = getViewById(R.id.tv_house_price_trend);
        //在售新房楼盘数
        tvHouseNewNum = getViewById(R.id.tv_house_new_num);
        //均价上涨板块数
        tvHousePriceUpNum = getViewById(R.id.tv_house_module_up_num);
        //均价下跌板块数
        tvHousePriceDownNum = getViewById(R.id.tv_house_module_down_num);
        //30天环比图标
        ivHousePriceTrend = getViewById(R.id.iv_house_price_trend);
    }
}

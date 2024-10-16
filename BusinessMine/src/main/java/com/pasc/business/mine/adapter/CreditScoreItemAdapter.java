package com.pasc.business.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.CreditScoreItem;

import java.util.List;

/**
 * Created by xiongjiaping112 on 17/12/15.
 * <p>
 * Modified by huangtebian535 on 2018/06/08.
 */

public class CreditScoreItemAdapter extends BaseQuickAdapter<CreditScoreItem, BaseViewHolder> {
    public CreditScoreItemAdapter(@Nullable List<CreditScoreItem> data) {
        super(R.layout.mine_item_credit_score, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditScoreItem item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        icon.setImageResource(item.icon);
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_desc, item.desc);
        helper.setText(R.id.tv_score, "");
    }
}

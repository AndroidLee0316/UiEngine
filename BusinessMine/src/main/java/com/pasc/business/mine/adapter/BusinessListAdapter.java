package com.pasc.business.mine.adapter;

import android.os.Build;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.BusinessItem;

import java.util.ArrayList;

/**
 * Created by ruanwei489 on 2018/1/23.
 */

public class BusinessListAdapter extends BaseQuickAdapter<BusinessItem, BaseViewHolder> {

    public BusinessListAdapter(ArrayList<BusinessItem> datas) {
        super(R.layout.mine_item_business_content, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessItem item) {
        ((TextView) helper.getView(R.id.business_title)).getPaint().setFakeBoldText(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((TextView) helper.getView(R.id.business_title)).setLetterSpacing(0.07f);
        }
        helper.setText(R.id.business_title, item.title)
                .setText(R.id.business_time, item.time);
    }
}

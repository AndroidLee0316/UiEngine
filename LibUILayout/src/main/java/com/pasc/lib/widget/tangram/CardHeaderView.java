package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.lib.widget.tangram.BaseCardView;

@Deprecated
public class CardHeaderView extends BaseCardView {

    ImageView mIcon;
    TextView mDesc;
    TextView mTitle;
    View mTopDivider;

    public CardHeaderView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_main_module_header, this);

        mTopDivider = getViewById(R.id.top_divider);
        mIcon = getViewById(R.id.iv_main_head);
        mDesc = getViewById(R.id.intro);
        mTitle = getViewById(R.id.module_title);
    }
}

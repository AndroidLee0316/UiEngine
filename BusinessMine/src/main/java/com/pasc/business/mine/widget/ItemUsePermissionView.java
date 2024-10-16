package com.pasc.business.mine.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pasc.business.mine.R;

/**
 * 功能：系统权限item栏view
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2019/7/26
 */
public class ItemUsePermissionView extends FrameLayout {

    //标题
    private TextView mTitleTV;
    //副标题
    private TextView mSubTitleTV;
    //状态栏
    private TextView mStatusTV;

    public ItemUsePermissionView(@NonNull Context context) {
        super(context);
    }

    public ItemUsePermissionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.mine_item_use_permission,this);
        mTitleTV = view.findViewById(R.id.item_up_title_tv);
        mSubTitleTV = view.findViewById(R.id.item_up_subtitle_tv);
        mStatusTV = view.findViewById(R.id.item_up_status_tv);
    }


    public ItemUsePermissionView setTitle(String title){
        mTitleTV.setText(title);
        return this;
    }

    public ItemUsePermissionView setTitle(int title){
        mTitleTV.setText(title);
        return this;
    }


    public ItemUsePermissionView setSubTitle(String subTitle){
        mSubTitleTV.setText(subTitle);
        return this;
    }

    public ItemUsePermissionView setSubTitle(int subTitle){
        mSubTitleTV.setText(subTitle);
        return this;
    }


    public ItemUsePermissionView setStatus(String status){
        mStatusTV.setText(status);
        return this;
    }

    public ItemUsePermissionView setStatus(int status){
        mStatusTV.setText(status);
        return this;
    }


}

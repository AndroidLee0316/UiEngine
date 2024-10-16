package com.pasc.business.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.LocalPicture;
import com.pasc.lib.base.util.DensityUtils;
import com.pasc.lib.imageloader.PascImageLoader;

import java.util.List;

/**
 * Created by ex-lingchun001 on 2018/3/6.
 */

public class LocalPictureAdapter extends BaseQuickAdapter<LocalPicture, BaseViewHolder> {

    private int canSelect;
    private int hasSelected = 0;
    private List<LocalPicture> list;

    private int spanCount=0;
    private int displayWidt=1080;
    private int itemW,itemH;
    public LocalPictureAdapter(Context context, int spanCount, @Nullable List<LocalPicture> data, int canSelect, List<LocalPicture> list) {
        super(R.layout.mine_item_local_picture, data);
        this.canSelect = canSelect;
        this.list = list;
        this.spanCount=spanCount;
        displayWidt= DensityUtils.getScreenWidth(context);
        if (spanCount>0){
        itemW=itemH=displayWidt/spanCount;}
    }
    @Override
    protected void convert(BaseViewHolder helper, LocalPicture item) {
        PascImageLoader.getInstance().loadLocalImage(item.path, (ImageView) helper.getView(R.id.img_local), R.drawable.mine_bg_default_image_color, PascImageLoader.SCALE_CENTER_CROP);
        helper.getView(R.id.tv_position).setSelected(item.isSelect());
        helper.setText(R.id.tv_position, getSelectPosition(item));
        if (hasSelected >= canSelect) {
            if (item.isSelect()) {
                helper.itemView.setAlpha(1);
            } else {
                helper.itemView.setAlpha(0.4f);
            }
        } else {
            helper.itemView.setAlpha(1);
        }

        helper.addOnClickListener(R.id.fl_icon).addOnClickListener(R.id.img_local);
        if (spanCount>0) {
            View flPicture = helper.getView(R.id.fl_picture);
            ViewGroup.LayoutParams layoutParams=  flPicture.getLayoutParams();
            layoutParams.width=itemW;
            layoutParams.height=itemH;
            flPicture.setLayoutParams(layoutParams);

        }
    }

    public void notifyDataChanged(int hasSelected, List<LocalPicture> list) {
        this.hasSelected = hasSelected;
        this.list = list;
        notifyDataSetChanged();
    }

    private String getSelectPosition(LocalPicture picture) {

        for (int i = 0; i < list.size(); i++) {
            LocalPicture localPicture = list.get(i);
            if (localPicture.equals(picture)) {
                return String.valueOf(i + 1);
            }
        }
        return "";
    }
}

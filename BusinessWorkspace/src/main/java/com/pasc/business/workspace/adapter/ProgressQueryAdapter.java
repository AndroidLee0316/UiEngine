package com.pasc.business.workspace.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.workspace.bean.MyAffairItem;
import com.pasc.business.workspace.R;

import java.util.List;

/**
 * 首页进度查询适配器，目前使用在 "进度查询" 有使用.
 * <p>
 * 限制视图数量，最多显示2项.
 * Modified by chenruihan410 on 2018/08/12.
 * <p>
 * 修改我的业务列表样式.
 * Modified by chenruihan410 on 2018/09/26.
 */
public class ProgressQueryAdapter extends BaseQuickAdapter<MyAffairItem, BaseViewHolder> {
    List<MyAffairItem> data;

    public ProgressQueryAdapter(List<MyAffairItem> data) {
        super(R.layout.workspace_item_main_news_item, data);
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return Math.min(2, super.getItemCount()); // 最多取两项
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAffairItem affairItem) {
        TextView tvTitle = helper.getView(R.id.tv_progress_query_title);
        TextView tvDate = helper.getView(R.id.tv_progress_query_time);
        ImageView statusIcon = helper.getView(R.id.statusIcon);

        helper.getView(R.id.tv_progress_query_divider)
                .setVisibility(affairItem == data.get(data.size() - 1) ? View.GONE : View.VISIBLE);

        //进度查询（社保）
        tvTitle.setText(affairItem.deptYwName);
        tvDate.setText(affairItem.applyDate);
        int status = affairItem.statuNo;
        switch (status) {
            default:
            case MyAffairItem.TYPE_WAIT_AUDIT://审核中
                statusIcon.setImageResource(R.drawable.workspace_ic_wait_audit);
                break;
            case MyAffairItem.TYPE_FINISHED://已完成
                statusIcon.setImageResource(R.drawable.workspace_ic_finished);
                break;
            case MyAffairItem.TYPE_BACK://已驳回
                statusIcon.setImageResource(R.drawable.workspace_ic_reject);
                break;

        }
    }
}

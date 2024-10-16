package com.pasc.business.workspace.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.business.workspace.R;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

import java.util.List;

/**
 * 政民互动 新闻   by zc 2018年05月11日16:19:51
 */

public class InteractionNewsAdapter
        extends BaseMultiItemQuickAdapter<InteractionNewsBean, BaseViewHolder> {
    //1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯 ；5：大图类型
    public final static int TYPE_01 = 1;
    public final static int TYPE_02 = 2;
    public final static int TYPE_03 = 3;
    public final static int TYPE_04 = 4;
    public final static int TYPE_05 = 5;

    public static final String regex = ",";

    public InteractionNewsAdapter(List<InteractionNewsBean> data) {
        super(data);
        //addItemType(TYPE_01, R.layout.item_news_type_01);//大图视频
        addItemType(TYPE_01, R.layout.workspace_item_news_type_normal);//大图视频
        addItemType(TYPE_02, R.layout.workspace_item_news_type_02);//多图
        addItemType(TYPE_03, R.layout.workspace_item_news_type_03);//单图
        addItemType(TYPE_04, R.layout.workspace_item_news_type_normal);//无图
        addItemType(TYPE_05, R.layout.workspace_item_news_type_01);//无图
    }

    @Override
    protected void convert(BaseViewHolder helper, InteractionNewsBean item) {
        String bottomText = "";
        if (item != null) {
            String origin = TextUtils.isEmpty(item.getOrigin()) ? "" : item.getOrigin() + "  ";
            String issueDate = TextUtils.isEmpty(item.getIssueDate()) ? "" : item.getIssueDate();
            bottomText = origin + issueDate;
        }

        int position = getData().indexOf(item); //当前加载的position

        switch (helper.getItemViewType()) {
            case TYPE_01:
                if (item != null) {

                    helper.setText(R.id.tv_title, item.getTitle())
                            .setVisible(R.id.rtv_tag, true)
                            .setText(R.id.tv_publish, bottomText);

                    helper.setVisible(R.id.v_divider, position != getData().size() - 1);
                }
                break;

            case TYPE_02:
                if (item != null) {
                    helper.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_publish, bottomText);
                    String[] urlList = splitUrl(item.getResourceLinks());
                    if (urlList != null) {
                        for (int i = 0; i < urlList.length; i++) {
                            if (i == 0) {
                                PascImageLoader.getInstance().loadImageUrl(urlList[i],
                                        (ImageView)helper.getView(R.id.iv_01), PascImageLoader.SCALE_CENTER_CROP);
                            } else if (i == 1) {
                                PascImageLoader.getInstance().loadImageUrl(urlList[i],
                                        (ImageView)helper.getView(R.id.iv_02), PascImageLoader.SCALE_CENTER_CROP);
                            } else if (i == 2) {
                                PascImageLoader.getInstance().loadImageUrl(urlList[i],
                                        (ImageView)helper.getView(R.id.iv_03), PascImageLoader.SCALE_CENTER_CROP);
                            }
                        }
                    }
                    helper.setVisible(R.id.v_divider, position != getData().size() - 1);
                }
                break;

            case TYPE_03:
                if (item != null) {
                    helper.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_publish, bottomText);
                    String[] urlList = splitUrl(item.getResourceLinks());
                    if (urlList != null && urlList.length > 0) {
                        PascImageLoader.getInstance().loadImageUrl(urlList[0],
                                (ImageView)helper.getView(R.id.iv_icon), PascImageLoader.SCALE_CENTER_CROP);
                    }
                    helper.setVisible(R.id.v_divider, position != getData().size() - 1);
                }
                break;
            default:
            case TYPE_04:
                if (item != null) {
                    helper.setText(R.id.tv_title, item.getTitle())
                            .setGone(R.id.rtv_tag, false)
                            .setText(R.id.tv_publish, bottomText);
                    helper.setVisible(R.id.v_divider, position != getData().size() - 1);
                }
                break;

            case TYPE_05:
                if (item != null) {
                    helper.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_publish, bottomText);
                    String[] urlList = splitUrl(item.getResourceLinks());
                    if (urlList != null && urlList.length > 0) {
                        PascImageLoader.getInstance().loadImageUrl(urlList[0],
                                (ImageView)helper.getView(R.id.iv_icon), PascImageLoader.SCALE_CENTER_CROP);
                    }
                    helper.setVisible(R.id.v_divider, position != getData().size() - 1);
                }
                break;
        }
    }

    private String[] splitUrl(String urls) {
        if (urls != null) {
            return urls.split(regex);
        } else {
            return null;
        }
    }
}

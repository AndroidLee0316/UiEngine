package com.pasc.pascuiengine.impl.converter;

import android.text.TextUtils;

import com.pasc.lib.newscenter.bean.NewsInfoBean;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InteractionNewsBeanConverter {

    public InteractionNewsBean from(NewsInfoBean newsInfo) {
        InteractionNewsBean bean = new InteractionNewsBean();
        bean.setIsTop(newsInfo.isTop);
        bean.setTitle(newsInfo.title);
        bean.setInformationLinkH5(newsInfo.articleLink);
        bean.setOrigin(newsInfo.source);
        String issueDate = newsInfo.issueDate;
        if (!TextUtils.isEmpty(issueDate)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                issueDate = dateFormat.format(new Date(Long.valueOf(issueDate)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        bean.setIssueDate(issueDate);
        bean.setResourceLinks(newsInfo.titlePicture);
        if (!TextUtils.isEmpty(newsInfo.columnType)) {
            bean.setType(Integer.parseInt(newsInfo.columnType));
        }
        return bean;
    }

    public List<InteractionNewsBean> from(List<NewsInfoBean> listData) {
        List<InteractionNewsBean> news = new ArrayList<InteractionNewsBean>();
        if (listData != null) {
            for (NewsInfoBean item : listData) {
                news.add(from(item));
            }
        }
        return news;
    }
}

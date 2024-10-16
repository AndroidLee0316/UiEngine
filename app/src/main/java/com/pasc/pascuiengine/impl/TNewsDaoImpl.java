package com.pasc.pascuiengine.impl;

import com.pasc.lib.newscenter.NewsCenterManager;
import com.pasc.lib.newscenter.bean.NewsInfoBean;
import com.pasc.lib.newscenter.bean.NewsListInfoBean;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.pascuiengine.impl.converter.InteractionNewsBeanConverter;

import java.util.List;

public class TNewsDaoImpl implements NewsDao {

    @Override
    public List<InteractionNewsBean> getNews() {
        NewsListInfoBean newsListInfoBean = NewsCenterManager.getInstance().getTopNewsListFromNet("1");
        List<NewsInfoBean> listData = newsListInfoBean.getListData();
        InteractionNewsBeanConverter interactionNewsBeanConverter = new InteractionNewsBeanConverter();
        List<InteractionNewsBean> news = interactionNewsBeanConverter.from(listData);
        return news;
    }
}

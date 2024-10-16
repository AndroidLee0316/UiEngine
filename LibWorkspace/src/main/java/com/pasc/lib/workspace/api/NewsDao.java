package com.pasc.lib.workspace.api;

import com.pasc.lib.workspace.bean.InteractionNewsBean;

import java.util.List;

public interface NewsDao {
    List<InteractionNewsBean> getNews();
}

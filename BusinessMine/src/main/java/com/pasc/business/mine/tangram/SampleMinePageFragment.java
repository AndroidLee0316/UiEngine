package com.pasc.business.mine.tangram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Toast;
import com.pasc.business.mine.R;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.share.ShareManager;
import com.pasc.lib.share.config.ShareContent;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.workspace.handler.event.GoToMyAffairEvent;
import com.pasc.lib.workspace.handler.event.GoToMyCardEvent;
import com.pasc.lib.workspace.handler.event.GoToMyFollowEvent;
import com.pasc.lib.workspace.handler.event.GoToMyPayEvent;
import com.pasc.lib.workspace.handler.event.GoToMyRegisterEvent;
import com.pasc.lib.workspace.handler.event.GoToShareEvent;
import com.pasc.lib.workspace.handler.event.SimpleClickEvent;
import com.tmall.wireless.tangram.eventbus.BusSupport;
import com.tmall.wireless.tangram.eventbus.Event;
import com.tmall.wireless.tangram.eventbus.EventHandlerWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.pasc.business.mine.tangram.bean.ICardPkgBean.TYPE_CARD_BASE;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 * <p>
 * 我的Fragment，使用Tangram技术.
 */
public class SampleMinePageFragment extends BaseMinePageFragment {

  private EventHandlerWrapper onHorizontalScrollItemClickHandler;

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public String getConfigId() {
    return "pasc.smt.minepage";
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyCardEvent(GoToMyCardEvent event) {
    // 跳转我的卡包
    Toast.makeText(getContext(), "功能暂未实现", Toast.LENGTH_SHORT).show();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyAffairEvent(GoToMyAffairEvent event) {
    //@TODO 跳转我的办事
    Toast.makeText(getContext(), "我的办事", Toast.LENGTH_SHORT).show();
    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SERVICE_1);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyFollowEvent(GoToMyFollowEvent event) {
    //@TODO 跳转我的收藏
    Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show();

    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SERVICE_4);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyPayEvent(GoToMyPayEvent event) {
    //@TODO 跳转我的缴费
    Toast.makeText(getContext(), "我的缴费", Toast.LENGTH_SHORT).show();
    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SERVICE_3);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyRegisterEvent(GoToMyRegisterEvent event) {
    //@TODO 跳转我的预约
    Toast.makeText(getContext(), "我的预约", Toast.LENGTH_SHORT).show();
    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SERVICE_2);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyPraiseEvent(GoToShareEvent event) {
    // 跳转分享
    ShareContent.Builder contentBuilder = new ShareContent.Builder();
    contentBuilder.setTitle("测试页面标题")
        .setContent("测试页面摘要")
        .setShareUrl("https://www.baidu.com")
        .setImageUrl("https://www.baidu.com/img/bd_logo1.png?qua=high&where=super")
        //短信内容分享定制
        .setSmsContent("测试页面摘要" + "https://www.baidu.com")
        //邮件分享内容定制
        .setEmailContent("页面摘要+详情点击（" + "https://www.baidu.com" + ")")
        //邮件分享标题定制
        .setEmailTitle("来自i深圳的分享：+页面标题")
        //复制链接定制
        .setCopyLinkUrl("https://www.baidu.com");
    ShareManager.getInstance()
        .shareContent(getActivity(), contentBuilder.build(), null);
    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SHARE);
  }
}

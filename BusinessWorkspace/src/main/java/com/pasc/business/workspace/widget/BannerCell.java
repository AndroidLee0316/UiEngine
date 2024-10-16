package com.pasc.business.workspace.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.business.workspace.R;
import com.pasc.business.workspace.constants.BannerArgKey;
import com.pasc.business.workspace.widget.event.BannerItemClickEvent;
import com.pasc.lib.base.util.CollectionUtils;
import com.pasc.lib.widget.banner.SliderLayout;
import com.pasc.lib.widget.banner.indicators.PagerIndicator;
import com.pasc.lib.widget.tangram.BaseCardCell;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.pasc.lib.workspace.bean.BannerBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BannerCell extends BaseCardCell<BannerView> {

  private static final String TAG = "BannerCell";

  @Override
  protected Class getDataSourceType() {
    return BannerBean.class;
  }

  private SliderLayout.OnItemClickListener onItemClickListener =
      new SliderLayout.OnItemClickListener() {
        @Override
        public void onItemClick(int index) {
          try {
            // 跳转处理
            Activity activity = CellUtils.getActivity(null, BannerCell.this);
            if (activity != null) {
              List dataSource = getListDataSource();
              Object item = dataSource.get(index);
              if (item instanceof BannerBean) {
                BannerBean bannerBean = (BannerBean) item;
                ArrayMap<String, String> args = new ArrayMap<>();
                args.put(BannerArgKey.ID, String.valueOf(bannerBean.getId()));
                args.put(BannerArgKey.PIC_NAME, bannerBean.getPicName());
                args.put(BannerArgKey.PIC_URL, bannerBean.getPicUrl());
                args.put(BannerArgKey.PIC_SKIP_URL, bannerBean.getPicSkipUrl());
                args.put(BannerArgKey.SERVICE_ID, bannerBean.getServiceId());
                CellUtils.postEvent(BannerCell.this, "onBannerItemClick", args);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };

  @Override
  public void parseStyle(@Nullable JSONObject data) {
    super.parseStyle(data);
  }

  @Override
  protected void bindViewData(final @NonNull BannerView view) {
    super.bindViewData(view);

    if (view == null) return;
    Context context = view.getContext();

    // 设置指示器的位置
    String indicatorPosition = optStringParam("indicatorPosition");
    SliderLayout.PresetIndicators indicator = getIndicator(indicatorPosition);
    view.sliderLayout.setPresetIndicator(indicator);

    // 设置指示器的padding
    int[] indicatorPadding = null;
    String indicatorPaddingKey = "indicatorPadding";
    if (extras.has(indicatorPaddingKey)) {
      indicatorPadding = CellUtils.getPaddingFromJson(extras, indicatorPaddingKey);
    } else if (style != null && style.extras != null && style.extras.has(indicatorPaddingKey)) {
      indicatorPadding = CellUtils.getPaddingFromJson(style.extras, indicatorPaddingKey);
    }
    if (indicatorPadding != null && indicatorPadding.length >= 4) {
      view.sliderLayout.setIndicatorPadding(indicatorPadding[3], indicatorPadding[0],
          indicatorPadding[1], indicatorPadding[2]);
    } else {
      int defaultPaddingValue = AndroidUtils.dip2px(context, 8);
      view.sliderLayout.setIndicatorPadding(defaultPaddingValue, defaultPaddingValue,
          defaultPaddingValue, defaultPaddingValue);
    }

    // 设置指示器样式
    PagerIndicator pagerIndicator = view.sliderLayout.getPagerIndicator();
    if (pagerIndicator != null) {
      int selectedDrawable = 0;
      int unSelectedDrawable = 0;

      unSelectedDrawable = optResParam(context, "indicatorNormalRes", unSelectedDrawable);
      selectedDrawable = optResParam(context, "indicatorSelectedRes", selectedDrawable);

      int selectedIndicatorResId = pagerIndicator.getSelectedIndicatorResId();
      int unSelectedIndicatorResId = pagerIndicator.getUnSelectedIndicatorResId();
      if (selectedIndicatorResId != selectedDrawable
          && unSelectedIndicatorResId != unSelectedDrawable) {
        pagerIndicator.setIndicatorStyleResource(selectedDrawable, unSelectedDrawable);
      }
    }

    // 新增圆角的配置
    int radiusDp = optIntParam("radius");
    int radiusPx = AndroidUtils.dip2px(context, radiusDp);
    view.sliderLayout.setImgRadius(radiusPx);

    // 比例高度
    String ratioStr = optStringParam("ratio");
    if (!TextUtils.isEmpty(ratioStr)) {
      try {
        Float ratio = Float.valueOf(ratioStr);
        view.sliderLayout.setRatio(ratio);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    float ratio = view.sliderLayout.getRatio();
    Log.d(TAG, "广告图的宽高比是：" + ratio);
    if (ratio != Float.NaN) {
      int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
      view.sliderLayout.getLayoutParams().height = (int) (widthPixels / ratio);
    }

    // 操作栏设置
    boolean actionsVisible = optBoolParam("actionsVisible");
    view.setActionsVisible(actionsVisible);
    if (actionsVisible) {
      try {
        JSONArray actions = extras.getJSONArray("actions");
        int length = actions.length();
        int size = Math.min(length, 4); // 最多4个

        for (int i = 0; i < size; i++) {
          JSONObject jsonObject = actions.getJSONObject(i);
          ImageView icon = null;
          TextView title = null;
          View action = null;
          if (i == 0) {
            icon = view.icon0;
            title = view.title0;
            action = view.action0;
          } else if (i == 1) {
            icon = view.icon1;
            title = view.title1;
            action = view.action1;
          } else if (i == 2) {
            icon = view.icon2;
            title = view.title2;
            action = view.action2;
          } else if (i == 3) {
            icon = view.icon3;
            title = view.title3;
            action = view.action3;
          }
          setImageAndStyle(view, icon, "icon", jsonObject);
          setTextAndStyle(view, title, "title", jsonObject);

          action.setTag(jsonObject);
          action.setOnClickListener(this);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    // 图框设置
    boolean imgFrameVisible = optBoolParam("imgFrameVisible");
    view.setImgFrameVisible(imgFrameVisible);
    if (imgFrameVisible) {
      String imgFrame = optStringParam("imgFrame");
      view.setImgFrame(imgFrame);
    }

    // 遮盖物设置
    boolean shelterVisible = optBoolParam("shelterVisible");
    view.setShelterVisible(shelterVisible);
    if (shelterVisible) {
      String shelter = optStringParam("shelter");
      view.setShelter(shelter);
    }

    // 默认图片的设置
    int imgDefaultRes = R.drawable.workspace_img_default_banner;
    String imgDefaultResProtocol = optStringParam("imgDefaultRes");
    if (!TextUtils.isEmpty(imgDefaultResProtocol)) {
      Integer imgDefaultResConfig = ConfigUtils.getDrawableFromUrl(context, imgDefaultResProtocol);
      if (imgDefaultResConfig != null) {
        imgDefaultRes = imgDefaultResConfig;
      }
    }
    view.setDefaultImgRes(imgDefaultRes);

    List dataSource = getListDataSource();
    if (CollectionUtils.isNotEmpty(dataSource)) {
      int size = dataSource.size();
      if (size > 1) {
        view.sliderLayout.setAutoCycle(true);
        view.sliderLayout.setIndicatorVisible(true);
        view.sliderLayout.setEnableHandSlide(true);
      } else {
        view.sliderLayout.setAutoCycle(false);
        view.sliderLayout.setIndicatorVisible(false);
        view.sliderLayout.setEnableHandSlide(false);
      }

      String[] urls = new String[size];
      for (int i = 0; i < size; i++) {
        Object item = dataSource.get(i);
        if (item instanceof BannerBean) {
          urls[i] = ((BannerBean) item).getPicUrl();
        }
      }
      view.sliderLayout.setImageUrls(urls);
      view.sliderLayout.setOnItemClickListener(onItemClickListener);
    } else {
      view.sliderLayout.setEnableHandSlide(false);
      view.sliderLayout.setAutoCycle(false);
      view.sliderLayout.setIndicatorVisible(false);
      view.sliderLayout.setImages(new int[] { imgDefaultRes });
    }
  }

  private SliderLayout.PresetIndicators getIndicator(String indicatorPosition) {
    if ("bottomCenter".equals(indicatorPosition)) {
      return SliderLayout.PresetIndicators.Center_Bottom;
    } else if ("bottomLeft".equals(indicatorPosition)) {
      return SliderLayout.PresetIndicators.Left_Bottom;
    }
    return SliderLayout.PresetIndicators.Right_Bottom;
  }
}

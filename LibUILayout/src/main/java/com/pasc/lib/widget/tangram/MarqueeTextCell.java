package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.eventbus.BusSupport;
import com.tmall.wireless.tangram.eventbus.Event;
import com.tmall.wireless.tangram.eventbus.EventContext;

import java.util.List;

public class MarqueeTextCell extends BaseCardCell<MarqueeTextView> {

  public static final String ARROW = "arrow";
  public static final String CLOSE = "close";

  @Override
  protected Class getDataSourceType() {
    return MarqueeTextModel.class;
  }

  @Override
  public boolean isValid() {
    return super.isValid();
  }

  @Override
  public void onClick(View v) {
    if (isCanJump()) {
      postClickEvent(ARROW, getArgs());
    }else {
      Log.e(MarqueeTextCell.class.getSimpleName(),"url is empty, click return");
    }
  }

  private boolean isCanJump() {
    MarqueeTextModel model = getModel();
    if (model != null) {
      if (!TextUtils.isEmpty(model.getSkipUrl())) {
        return true;
      }
    }
    return false;
  }

  private ArrayMap<String, String> getArgs() {
    ArrayMap<String, String> args = new ArrayMap<>();
    MarqueeTextModel model = getModel();
    if (model != null) {
      args.put("id", model.getId());
      args.put("skipUrl", model.getSkipUrl());
      args.put("title", model.getTitle());
      args.put("closable", String.valueOf(model.isClosable()));
    }
    return args;
  }

  private void postClickEvent(String eventId, ArrayMap<String, String> args) {
    CellUtils.postEvent(this, eventId, args);
  }

  private MarqueeTextModel getModel() {
    List listDataSource = getListDataSource();
    if (listDataSource == null || listDataSource.size() == 0) {
      return null;
    }
    MarqueeTextModel marqueeTextModel = (MarqueeTextModel) listDataSource.get(0);
    return marqueeTextModel;
  }

  @Override
  protected void bindViewClickListener(View view) {
    view.setOnClickListener(this);
  }

  @Override
  protected void bindViewData(final @NonNull MarqueeTextView view) {
    super.bindViewData(view);

    MarqueeTextModel marqueeTextModel = getModel();
    if (marqueeTextModel == null) {
      view.getLayoutParams().height = 0;
      view.setVisibility(View.GONE);
      return;
    } else {
      view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
      view.setVisibility(View.VISIBLE);
    }

    view.setIconState(getIconState(marqueeTextModel));

    view.setTitle(marqueeTextModel.getTitle());

    view.getIconContainer().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isClosable()) {
          postClickEvent(CLOSE, getArgs());
        }else if (isCanJump()){
          postClickEvent(ARROW, getArgs());
        }else {
          Log.e(MarqueeTextCell.class.getSimpleName(),"url is empty, click return");
        }
      }
    });
  }

  private boolean isClosable() {
    MarqueeTextModel model = getModel();
    if (model != null) {
      return model.isClosable();
    }
    return false;
  }

  private MarqueeTextView.IconState getIconState(MarqueeTextModel marqueeTextModel) {
    String skipUrl = marqueeTextModel.getSkipUrl();
    if (TextUtils.isEmpty(skipUrl)) {
      boolean closable = marqueeTextModel.isClosable();
      if (closable) {
        return MarqueeTextView.IconState.CLOSE;
      } else {
        return MarqueeTextView.IconState.HIDE;
      }
    } else {
      boolean closable = marqueeTextModel.isClosable();

      if (closable) {
        return MarqueeTextView.IconState.CLOSE;
      } else {
        return MarqueeTextView.IconState.ARROW;
      }
    }
  }
}

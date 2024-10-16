package com.pasc.lib.workspace.handler.impl;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.pasc.lib.workspace.handler.TangramClickHandler;
import com.pasc.lib.workspace.handler.TangramClickParams;
import com.pasc.lib.workspace.handler.event.BaseEvent;
import com.pasc.lib.workspace.handler.event.GoToAccumulationFundEvent;
import com.pasc.lib.workspace.handler.event.GoToBikeEvent;
import com.pasc.lib.workspace.handler.event.GoToElectricityFeesEvent;
import com.pasc.lib.workspace.handler.event.GoToLiveLiHoodEvent;
import com.pasc.lib.workspace.handler.event.GoToSocialSecurityEvent;
import com.pasc.lib.workspace.handler.event.GoToThreeExceedPapersEvent;
import com.pasc.lib.workspace.handler.event.GoToViolationEvent;
import com.pasc.lib.workspace.handler.event.GoToWaterFeesEvent;
import com.pasc.lib.workspace.handler.event.NotValidEvent;
import com.pasc.lib.workspace.handler.event.SimpleClickEvent;
import com.pasc.lib.workspace.handler.util.ProtocolUtils;
import com.tmall.wireless.tangram.structure.BaseCell;

import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 事件处理器。
 */
public class TCEventHandlerImpl implements TangramClickHandler {

  private static final String TAG = "TCEventHandlerImpl";

  public TCEventHandlerImpl() {
  }

  @Override
  public void handle(final TangramClickParams params) {
    if (params == null) return;
    String clickUrl = params.getClickUrl();
    JSONObject clickParams = params.getClickParams();

    if (TextUtils.isEmpty(clickUrl)) return;

    if (clickUrl.equals("event://Close")) {
      // 关闭组件
      View targetView = params.getTargetView();
      if (targetView != null) {
        targetView.post(new Runnable() {
          @Override
          public void run() {
            BaseCell cell = params.getCell();
            if (cell != null && cell.parent != null) {
              cell.parent.removeCell(cell);
            }
          }
        });
      }
      return;
    }

    Object event = buildEvent(clickUrl);
    if (event == null) Log.d(TAG, "事件协议无效?clickUrl=" + clickUrl);
    if (event == null) return;

    putParamsIntoEvent(event, clickParams, clickUrl);

    // 将cellId发送出去，有助于业务处理
    if (event instanceof BaseEvent) {
      BaseEvent baseEvent = (BaseEvent) event;
      BaseCell cell = params.getCell();
      if (cell != null) {
        baseEvent.put("cellType", cell.stringType);
        baseEvent.put("cellId", cell.id);
      }
    }

    EventBus.getDefault().post(event);
  }

  protected Object buildEvent(String url) {
    Pattern pattern = Pattern.compile("^event://(\\w*)(\\?.*)?$");
    Matcher matcher = pattern.matcher(url);
    if (matcher.find()) {
      String eventId = matcher.group(1);
      if (eventId != null) {
        if (eventId.equals("GoToSocialSecurity")) {
          return new GoToSocialSecurityEvent();
        } else if (eventId.equals("GoToAccumulationFund")) {
          return new GoToAccumulationFundEvent();
        } else if (eventId.equals("GoToViolation")) {
          return new GoToViolationEvent();
        } else if (eventId.equals("GoToWaterFees")) {
          return new GoToWaterFeesEvent();
        } else if (eventId.equals("GoToElectricityFees")) {
          return new GoToElectricityFeesEvent();
        } else if (eventId.equals("GoToThreeExceedPapers")) {
          return new GoToThreeExceedPapersEvent();
        } else if (eventId.equals("GoToLiveLiHood")) {
          return new GoToLiveLiHoodEvent();
        } else if (eventId.equals("GoToBike")) {
          return new GoToBikeEvent();
        } else if (eventId.equals("NotValid")) {
          return new NotValidEvent();
        } else if (eventId.equals("SimpleClick")) {
          return new SimpleClickEvent();
        } else {
          SimpleClickEvent simpleClickEvent = new SimpleClickEvent();
          simpleClickEvent.put("eventId", eventId);
          simpleClickEvent.put("eventUrl", url);
          return simpleClickEvent;
        }
      }
    }
    return null;
  }

  /**
   * 将参数放进事件对象.
   *
   * @param event 事件对象.
   * @param paramsJsonObject 参数对象.
   * @param url 地址.
   */
  public static void putParamsIntoEvent(Object event, JSONObject paramsJsonObject, String url) {
    if (event instanceof BaseEvent) {
      LinkedHashMap<String, String> params = ProtocolUtils.parseParams(url, paramsJsonObject);
      ProtocolUtils.handleParamsUrlPlaceholder(params);

      BaseEvent baseEvent = (BaseEvent) event;
      for (Map.Entry<String, String> entry : params.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        baseEvent.put(key, value);
      }
    }
  }
}

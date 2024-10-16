package com.pasc.lib.widget.tangram.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import com.pasc.lib.widget.tangram.R;

public class AndroidUtils {

  public static int dip2px(Context context, float dpValue) {
    float scale = getScreenDensity(context);
    return (int) (dpValue * scale + 0.5F);
  }

  public static float getScreenDensity(Context context) {
    return context.getResources().getDisplayMetrics().density;
  }

  public static int getPixel(float dpValue) {
    float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5F);
  }

  public static boolean isValidClick(View view) {
    if (view == null) return false;
    Object clickTime = view.getTag(R.integer.tag_key_click_time);
    long currentTime = System.currentTimeMillis();
    if (clickTime == null) {
      view.setTag(R.integer.tag_key_click_time, currentTime);
      return true;
    } else {
      if (clickTime instanceof Long) {
        long diff = currentTime - (Long) clickTime;
        if (diff >= 1000) {
          view.setTag(R.integer.tag_key_click_time, currentTime);
          return true;
        } else {
          return false;
        }
      } else {
        view.setTag(R.integer.tag_key_click_time, currentTime);
        return true;
      }
    }
  }
}

package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.lib.widget.PascRatioImageView;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

import cn.nekocode.dividerdrawable.DividerDrawable;
import cn.nekocode.dividerdrawable.DividerLayout;
import cn.nekocode.dividerdrawable.DividerUtils;

/**
 * 继承CardView的自定义视图所对应的Model.
 */
public class BaseCardCell<V extends BaseCardView> extends BasePascCell<V> {

  public static final String DIVIDER = "divider";
  public static final int DEFAULT_DIVIDER_COLOR = Color.parseColor("#e8e8e8");

  DividerDrawable topDivider;
  DividerDrawable rightDivider;
  DividerDrawable bottomDivider;
  DividerDrawable leftDivider;

  private int dividerColor;

  @Override
  protected void setViewBackground(V view) {
    super.setViewBackground(view);
    setViewBackgroundDivider(view);
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    dividerColor = getColor("dividerColor", DEFAULT_DIVIDER_COLOR);
  }

  protected void setViewBackgroundDivider(V view) {
    DividerUtils.clearDividersWith(view);
    boolean divider = getBoolean(extras, DIVIDER);
    Context context = view.getContext();
    int size = 0;
    boolean dividerTop = getBoolean(extras, DIVIDER + "Top", divider);
    boolean dividerRight = getBoolean(extras, DIVIDER + "Right", divider);
    boolean dividerBottom = getBoolean(extras, DIVIDER + "Bottom", divider);
    boolean dividerLeft = getBoolean(extras, DIVIDER + "Left", divider);
    if (dividerTop) size++;
    if (dividerRight) size++;
    if (dividerBottom) size++;
    if (dividerLeft) size++;
    if (size > 0) {
      DividerDrawable[] dividerDrawables = new DividerDrawable[size];
      int index = 0;
      if (dividerTop) {
        int dividerLength = getInt(extras, DIVIDER + "Top" + "Length");
        dividerDrawables[index++] = getTopDivider(context, dividerLength, dividerColor);
      }
      if (dividerRight) {
        int dividerLength = getInt(extras, DIVIDER + "Right" + "Length");
        dividerDrawables[index++] = getRightDivider(context, dividerLength, dividerColor);
      }
      if (dividerBottom) {
        int dividerLength = getInt(extras, DIVIDER + "Bottom" + "Length");
        dividerDrawables[index++] = getBottomDivider(context, dividerLength, dividerColor);
      }
      if (dividerLeft) {
        int dividerLength = getInt(extras, DIVIDER + "Left" + "Length");
        dividerDrawables[index] = getLeftDivider(context, dividerLength, dividerColor);
      }

      DividerUtils.addDividersTo(view, dividerDrawables);
    }
  }

  protected void setImage(ImageView imageView, ImgAttr imgAttr) {
    try {
      if (imageView == null || imgAttr == null) {
        return;
      }

      if (imageView instanceof PascRatioImageView) {
        PascRatioImageView ratioImageView = (PascRatioImageView) imageView;
        float heightRatio = imgAttr.getHeightRatio();
        ratioImageView.setHeightRatio(heightRatio);

        float widthRatio = imgAttr.getWidthRatio();
        ratioImageView.setWidthRatio(widthRatio);

        int ratioRef = imgAttr.getRatioRef();
        ratioImageView.setRatioRef(ratioRef);
      }

      if (imageView instanceof RoundedImageView) {
        RoundedImageView roundedImageView = (RoundedImageView) imageView;
        roundedImageView.setOval(imgAttr.isOval());
      }

      Context context = imageView.getContext();
      // 是否显示
      boolean visible = imgAttr.isVisible();
      int visibility = visible ? View.VISIBLE : View.GONE;
      imageView.setVisibility(visibility);

      if (visible) {
        ImageView.ScaleType scaleType = ImageView.ScaleType.valueOf(imgAttr.getScaleTypeName());
        if (scaleType != null) {
          imageView.setScaleType(scaleType);
        }

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        int width = imgAttr.getWidth();
        layoutParams.width = width;

        int height = imgAttr.getHeight();
        layoutParams.height = height;

        int[] margin = imgAttr.getMargin();
        if (margin != null && margin.length >= 4) {
          if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            int left = margin[3];
            int top = margin[0];
            int right = margin[1];
            int bottom = margin[2];
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(left, top,
                right, bottom);
          }
        } else {
          if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(0, 0, 0, 0);
          }
        }

        String defaultResFromConfig = imgAttr.getDefaultResFromConfig();
        Integer defaultRes = ConfigUtils.getDrawableFromUrl(context, defaultResFromConfig);
        if (defaultRes == null) {
          defaultRes = imgAttr.getDefaultResBuildIn();
          if (defaultRes == null) {
            defaultRes = buildDefaultRes(imageView);
          }
        }
        setDefaultImageTag(imageView, defaultRes);

        doLoadImageUrl(imageView, imgAttr.getUrl());
      }
    } catch (Exception e) {
      e.printStackTrace();
      // 如果发生异常则忽略，不影响下面的绑定逻辑
    }
  }

  /**
   * 设置图片和样式.
   *
   * @param cardView 卡片视图.
   * @param imageView 图片视图.
   * @param fieldName 字段名称.
   */
  @Deprecated
  protected void setImageAndStyle(BaseCardView cardView, ImageView imageView, String fieldName) {
    setImageAndStyle(cardView, imageView, fieldName, null);
  }

  @Deprecated
  protected void setImageAndStyle(BaseCardView cardView, ImageView imageView, String fieldName,
      JSONObject jsonObject, Integer defaultRes) {
    setImageAndStyle(cardView, imageView, fieldName, jsonObject, defaultRes, null);
  }

  @Deprecated
  protected void setImageAndStyle(BaseCardView cardView, ImageView imageView, String fieldName,
      JSONObject jsonObject, Integer defaultRes, String imgUrl) {
    try {
      if (jsonObject == null) {
        jsonObject = extras;
      }

      Context context = cardView.getContext();
      // 是否显示
      String visibleName = fieldName + "Visible";
      boolean visible = getBoolean(jsonObject, visibleName, true);
      if (visible) {
        imageView.setVisibility(View.VISIBLE);

        cardView.restoreImageSize(imageView);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        String widthName = fieldName + "Width";
        int width = getInt(jsonObject, widthName);
        if (width > 0) {
          layoutParams.width = AndroidUtils.dip2px(context, width);
        }
        String heightName = fieldName + "Height";
        int height = getInt(jsonObject, heightName);
        if (height > 0) {
          layoutParams.height = AndroidUtils.dip2px(context, height);
        }

        if (defaultRes == null) {
          setDefaultImageTag(imageView, fieldName);
        } else {
          setDefaultImageTag(imageView, defaultRes);
        }

        if (imgUrl == null) {
          String urlName = fieldName + "Url";
          imgUrl = getString(jsonObject, urlName);
        }
        doLoadImageUrl(imageView, imgUrl);
      } else {
        imageView.setVisibility(View.GONE);
      }
    } catch (Exception e) {
      e.printStackTrace();
      // 如果发生异常则忽略，不影响下面的绑定逻辑
    }
  }

  @Deprecated
  protected void setImageAndStyle(BaseCardView cardView, ImageView imageView, String fieldName,
      JSONObject jsonObject) {
    setImageAndStyle(cardView, imageView, fieldName, jsonObject, R.drawable.lwt_img_error);
  }

  @Deprecated
  protected void setTextAndStyle(BaseCardView cardView, TextView textView, String fieldName) {
    setTextAndStyle(cardView, textView, fieldName, null);
  }

  /**
   * 设置文本。
   *
   * @param textView 文本视图。
   * @param textAttr 文本属性。
   */
  protected void setText(TextView textView, TextAttr textAttr) {
    if (textView != null) {
      if (textAttr != null) {
        if (textAttr.isVisible()) {
          textView.setVisibility(View.VISIBLE);

          textView.setText(textAttr.getContent());
          textView.setTextSize(textAttr.getFontSizeUnit(), textAttr.getFontSize());
          textView.setTextColor(textAttr.getColor());
          textView.getPaint().setFakeBoldText(textAttr.isBold());
        } else {
          textView.setVisibility(View.GONE);
        }
      }
    }
  }

  @Deprecated
  protected void setTextAndStyle(BaseCardView cardView, TextView textView, String fieldName,
      JSONObject jsonObject) {
    setText(textView, fieldName, jsonObject);
    setTextSize(cardView, textView, fieldName, jsonObject);
    setTextColor(cardView, textView, fieldName, jsonObject);
    setTextBold(cardView, textView, fieldName, jsonObject);
  }

  private void setTextBold(BaseCardView cardView, TextView textView, String fieldName,
      JSONObject jsonObject) {
    try {
      if (jsonObject == null) {
        jsonObject = extras;
      }

      String name = fieldName + "Bold";
      boolean isBold = getBoolean(jsonObject, name, false);
      textView.getPaint().setFakeBoldText(isBold);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置字体大小.
   *
   * @param cardView 卡片视图.
   * @param textView 文本视图.
   * @param fieldName 字段名称.
   */
  protected void setTextSize(BaseCardView cardView, TextView textView, String fieldName) {
    setTextSize(cardView, textView, fieldName, null);
  }

  protected void setTextSize(BaseCardView cardView, TextView textView, String fieldName,
      JSONObject jsonObject) {
    try {
      if (jsonObject == null) {
        jsonObject = extras;
      }

      cardView.restoreTextSize(textView);
      String name = fieldName + "FontSize";
      int fontSize = getInt(jsonObject, name);
      if (fontSize > 0) {
        textView.setTextSize(fontSize);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置字体颜色.
   *
   * @param cardView 卡片视图.
   * @param textView 文本视图.
   * @param fieldName 字段名称.
   */
  protected void setTextColor(BaseCardView cardView, TextView textView, String fieldName) {
    setTextColor(cardView, textView, fieldName, null);
  }

  protected void setTextColor(BaseCardView cardView, TextView textView, String fieldName,
      JSONObject jsonObject) {
    try {
      cardView.restoreTextColor(textView);
      String name = fieldName + "Color";
      Integer color = getColor(name, jsonObject);
      if (color != null) {
        textView.setTextColor(color);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setText(TextView textView, String fieldName) {
    setText(textView, fieldName, null);
  }

  private void setText(TextView textView, String fieldName, JSONObject jsonObject) {
    if (jsonObject == null) {
      jsonObject = extras;
    }
    textView.setText(getString(jsonObject, fieldName));
  }

  private DividerDrawable getTopDivider(Context context, int lengthDp, int color) {
    topDivider = buildDividerIfNull(context, topDivider, color);
    setDividerStyle(topDivider, DividerLayout.ORIENTATION_HORIZONTAL,
        DividerLayout.ALIGN_PARENT_TOP, lengthDp);
    return topDivider;
  }

  private DividerDrawable buildDividerIfNull(Context context, DividerDrawable divider, int color) {
    if (divider == null) {
      divider = new DividerDrawable();
    }
    divider.setStrokeWidth(1).setColor(color);
    return divider;
  }

  private DividerDrawable getBottomDivider(Context context, int lengthDp, int color) {
    bottomDivider = buildDividerIfNull(context, bottomDivider, color);
    setDividerStyle(bottomDivider, DividerLayout.ORIENTATION_HORIZONTAL,
        DividerLayout.ALIGN_PARENT_BOTTOM, lengthDp);
    return bottomDivider;
  }

  private void setDividerStyle(DividerDrawable divider, int orientation, int align, int lengthDp) {
    DividerLayout layout = divider.getLayout();
    layout.setOrientation(orientation).setAlign(align);
    if (orientation == DividerLayout.ORIENTATION_VERTICAL) {
      layout.setCenter(DividerLayout.CENTER_VERTICAL);
    } else if (orientation == DividerLayout.ORIENTATION_HORIZONTAL) {
      layout.setCenter(DividerLayout.CENTER_HORIZONTAL);
    }
    if (lengthDp > 0) {
      layout.setLengthDp(lengthDp);
    } else {
      layout.setLength(-1);
    }
  }

  private DividerDrawable getRightDivider(Context context, int lengthDp, int color) {
    rightDivider = buildDividerIfNull(context, rightDivider, color);
    setDividerStyle(rightDivider, DividerLayout.ORIENTATION_VERTICAL,
        DividerLayout.ALIGN_PARENT_RIGHT, lengthDp);
    return rightDivider;
  }

  private DividerDrawable getLeftDivider(Context context, int lengthDp, int color) {
    leftDivider = buildDividerIfNull(context, leftDivider, color);
    setDividerStyle(leftDivider, DividerLayout.ORIENTATION_VERTICAL,
        DividerLayout.ALIGN_PARENT_LEFT, lengthDp);
    return leftDivider;
  }
}

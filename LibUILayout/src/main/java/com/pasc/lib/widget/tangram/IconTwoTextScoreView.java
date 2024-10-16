package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class IconTwoTextScoreView extends BaseCardView {

  private TextView titleView;
  private TextView descView;
  private ImageView iconView;

  public IconTwoTextScoreView(Context context) {
    super(context);
  }

  public IconTwoTextScoreView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_icon_two_text_view, this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
    }

    titleView = findViewById(R.id.titleView);
    descView = findViewById(R.id.descView);
    iconView = findViewById(R.id.iconView);
  }

  public TextView getTitleView() {
    return titleView;
  }

  public TextView getDescView() {
    return descView;
  }

  public ImageView getIconView() {
    return iconView;
  }
}

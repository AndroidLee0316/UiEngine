package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class IconTextNearbyServiceView extends BaseCardView {

  private ImageView iconView;
  private TextView titleView;

  public IconTextNearbyServiceView(Context context) {
    super(context);
  }

  public IconTextNearbyServiceView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.workspace_icon_text_nearby_service_view, this);

    iconView = findViewById(R.id.iconView);
    titleView = findViewById(R.id.titleView);
  }

  public ImageView getIconView() {
    return iconView;
  }

  public TextView getTitleView() {
    return titleView;
  }
}

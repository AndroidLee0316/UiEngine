package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoIconTextView extends BaseCardView {

  private ImageView iconView;
  private ImageView arrowIconView;
  private TextView titleView;

  public TwoIconTextView(Context context) {
    super(context);
  }

  public TwoIconTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_two_icon_text_view, this);

    arrowIconView = findViewById(R.id.arrowIconView);
    iconView = findViewById(R.id.iconView);
    titleView = findViewById(R.id.titleView);
  }

  public ImageView getIconView() {
    return iconView;
  }

  public ImageView getArrowIconView() {
    return arrowIconView;
  }

  public TextView getTitleView() {
    return titleView;
  }
}

package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoIconTwoTextView extends BaseCardView {

  private ImageView iconView;
  private ImageView arrowIconView;
  private TextView titleView;
  private TextView descView;
  private View lingGapView;

  public TwoIconTwoTextView(Context context) {
    super(context);
  }

  public TwoIconTwoTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context)
        .inflate(R.layout.workspace_two_icon_two_text_view, this);

    arrowIconView = findViewById(R.id.arrowIconView);
    iconView = findViewById(R.id.iconView);
    titleView = findViewById(R.id.titleView);
    descView = findViewById(R.id.descView);
    lingGapView = findViewById(R.id.lingGapView);
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

  public TextView getDescView() {
    return descView;
  }

  public View getLingGapView() {
    return lingGapView;
  }
}

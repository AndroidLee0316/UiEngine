package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleTextView extends BaseCardView {

  TextView titleView;
  private LinearLayout rootLayout;

  public SingleTextView(Context context) {
    super(context);
  }

  public SingleTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.item_main_single_text, this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
    }

    rootLayout = findViewById(R.id.rootLayout);
    titleView = findViewById(R.id.titleView);
  }

  public LinearLayout getRootLayout() {
    return rootLayout;
  }

  public void setGravity(String gravity) {
    int gravityValue = Gravity.LEFT;
    if ("center".equalsIgnoreCase(gravity)) {
      gravityValue = Gravity.CENTER;
    } else if ("right".equalsIgnoreCase(gravity)) {
      gravityValue = Gravity.RIGHT;
    }
    titleView.setGravity(gravityValue);
  }

  public TextView getTitleView() {
    return titleView;
  }
}

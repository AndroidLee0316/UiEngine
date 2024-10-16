package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuView extends BaseCardView {

  private LinearLayout menuLayout;
  private TextView titleView;
  private ImageView iconView;

  public MenuView(Context context) {
    super(context);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.workspace_menu_view, this);

    menuLayout = getViewById(R.id.menuLayout);
    iconView = getViewById(R.id.iconView);
    titleView = getViewById(R.id.titleView);
  }

  public LinearLayout getMenuLayout() {
    return menuLayout;
  }

  public TextView getTitleView() {
    return titleView;
  }

  public ImageView getIconView() {
    return iconView;
  }
}

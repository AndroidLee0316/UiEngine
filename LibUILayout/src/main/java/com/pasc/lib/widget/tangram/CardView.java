package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class CardView extends BaseCardView {

  private ImageView imgView;
  private TextView titleView;
  private TextView cardNameView;
  private TextView descView;
  private TextView actionDescView;

  public CardView(Context context) {
    super(context);
  }

  public CardView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_card_view, this);

    imgView = findViewById(R.id.imgView);
    titleView = findViewById(R.id.titleView);
    cardNameView = findViewById(R.id.cardNameView);
    descView = findViewById(R.id.descView);
    actionDescView = findViewById(R.id.actionDescView);
  }

  public ImageView getImgView() {
    return imgView;
  }

  public TextView getTitleView() {
    return titleView;
  }

  public TextView getCardNameView() {
    return cardNameView;
  }

  public TextView getDescView() {
    return descView;
  }

  public TextView getActionDescView() {
    return actionDescView;
  }
}

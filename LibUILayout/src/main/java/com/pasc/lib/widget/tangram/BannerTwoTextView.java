package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.widget.PascRatioImageView;

public class BannerTwoTextView extends BaseCardView {

  private PascRatioImageView bannerView;
  private TextView titleView;
  private TextView descView;
  private View lineGapView;
  private ViewGroup textLayout;

  public BannerTwoTextView(Context context) {
    super(context);
  }

  @Override
  protected void initViews(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.workspace_banner_two_text_view, this);

    bannerView = view.findViewById(R.id.bannerView);
    titleView = view.findViewById(R.id.titleView);
    lineGapView = view.findViewById(R.id.lineGapView);
    descView = view.findViewById(R.id.descView);
    textLayout = view.findViewById(R.id.textLayout);
  }

  public PascRatioImageView getBannerView() {
    return bannerView;
  }

  public TextView getTitleView() {
    return titleView;
  }

  public TextView getDescView() {
    return descView;
  }

  public View getLineGapView() {
    return lineGapView;
  }

  public ViewGroup getTextLayout() {
    return textLayout;
  }
}

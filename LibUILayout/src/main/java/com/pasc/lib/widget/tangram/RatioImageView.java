package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import com.pasc.lib.widget.PascRatioImageView;

public class RatioImageView extends BaseCardView {

  private PascRatioImageView ratioImageView;

  public RatioImageView(Context context) {
    super(context);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.workspace_ratio_image_view, this);

    ratioImageView = getViewById(R.id.ratioImageView);
  }

  public PascRatioImageView getRatioImageView() {
    return ratioImageView;
  }
}

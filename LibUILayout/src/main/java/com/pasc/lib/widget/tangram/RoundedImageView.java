package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

public class RoundedImageView extends BaseCardView {

  private com.makeramen.roundedimageview.RoundedImageView imgView;

  public RoundedImageView(Context context) {
    super(context);
  }

  public RoundedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_rounded_image_view, this);

    imgView = getViewById(R.id.imgView);
  }

  public com.makeramen.roundedimageview.RoundedImageView getImageView() {
    return imgView;
  }
}

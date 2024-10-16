package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pasc.lib.widget.PascRatioImageView;

public class ImgTextView extends BaseCardView {

  private PascRatioImageView img;
  private TextView title;
  private FrameLayout root;

  public ImgTextView(Context context) {
    super(context);
  }

  public ImgTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FrameLayout getRoot() {
    return root;
  }

  public PascRatioImageView getImg() {
    return img;
  }

  public TextView getTitle() {
    return title;
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.workspace_img_text_view, this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
    }

    root = findViewById(R.id.root);
    img = findViewById(R.id.img);
    title = findViewById(R.id.title);
  }
}

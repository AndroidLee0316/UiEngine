package com.pasc.business.mine.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pasc.business.mine.R;
import com.pasc.lib.base.util.DensityUtils;

/**
 * Created by zhangxu678 on 2018/12/17.
 */
public class IconLoadingDialog {
  private Context context;
  private Dialog dialog;
  TextView textView;
  View dialogView;
  ProgressBar progressBar;
  ImageView ivIcon;

  public IconLoadingDialog(Context context) {
    this(context, true);
  }

  public IconLoadingDialog(Context context, boolean isShowProgress) {

    this.context = context;
    this.dialog = new Dialog(context, R.style.common_loading_dialog);
    this.dialog.setContentView(R.layout.mine_layout_icon_loading);
    this.textView = this.dialog.findViewById(R.id.common_dialog_loading_textview);
    this.dialogView = this.dialog.findViewById(R.id.common_dialog_loading_relativelayout);
    this.progressBar = this.dialog.findViewById(R.id.common_dialog_loading_progressbar);
    this.ivIcon = this.dialog.findViewById(R.id.common_dialog_icon);
    this.progressBar.setVisibility(isShowProgress ? View.VISIBLE : View.INVISIBLE);
    this.ivIcon.setVisibility(!isShowProgress ? View.VISIBLE : View.INVISIBLE);
  }

  public IconLoadingDialog(Context context, String loadingMsg) {
    this(context);
    this.setContent(loadingMsg);
  }

  public IconLoadingDialog(Context context, int loadingMsgId) {
    this(context);
    this.setContent(loadingMsgId);
  }

  public void setHasContent(boolean hasContent) {
    if (hasContent) {
      this.textView.setVisibility(View.VISIBLE);
      ((RelativeLayout.LayoutParams) this.progressBar.getLayoutParams()).setMargins(0,
          DensityUtils.dp2px(24.0F), 0, 0);
      ((RelativeLayout.LayoutParams) this.dialogView.getLayoutParams()).width =
          DensityUtils.dp2px(220.0F);
    } else {
      this.textView.setVisibility(View.GONE);
      ((RelativeLayout.LayoutParams) this.progressBar.getLayoutParams()).setMargins(
          DensityUtils.dp2px(30.0F), DensityUtils.dp2px(30.0F), DensityUtils.dp2px(30.0F),
          DensityUtils.dp2px(30.0F));
    }
  }

  public void setLoadingDialogBackGround(int resourceId) {
    if (null != this.dialogView) {
      this.dialogView.setBackgroundResource(resourceId);
    }
  }

  public void setLoadingDialogBackGround(Drawable drawable) {
    if (null != this.dialogView) {
      this.dialogView.setBackgroundDrawable(drawable);
    }
  }

  public View getLoadingDialogView() {
    return this.dialogView;
  }

  public TextView getTextView() {
    return this.textView;
  }

  public void setContent(String content) {
    if (null != this.textView) {
      this.textView.setText(content);
    }
  }

  public void setContent(int resourceId) {
    if (null != this.textView) {
      this.textView.setText(resourceId);
    }
  }

  public void show() {
    if (!this.dialog.isShowing()) {
      this.dialog.show();
    }
  }

  public void hide() {
    this.dialog.hide();
  }

  public void dismiss() {
    this.dialog.dismiss();
  }

  public boolean isShowing() {
    return this.dialog.isShowing();
  }
}

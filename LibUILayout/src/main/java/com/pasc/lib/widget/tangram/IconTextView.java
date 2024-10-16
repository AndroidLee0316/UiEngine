package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconTextView extends BaseCardView {

    private ImageView iconView;
    private TextView mText;
    private LinearLayout labelLayout;

    public IconTextView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_icon_text_view, this);

        iconView = getViewById(R.id.iconView);
        mText = getViewById(R.id.tv_name);
        labelLayout = getViewById(R.id.labelLayout);
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getTitleView() {
        return mText;
    }

    private int getLabelViewId(String labelType) {
        if ("num".equalsIgnoreCase(labelType)) {
            return R.id.numView;
        } else if ("text".equalsIgnoreCase(labelType)) {
            return R.id.textView;
        } else {
            return R.id.dotView;
        }
    }

    public void setLabel(boolean labelVisible, String labelType, String label, int[] labelMargin) {
        int visible = labelVisible ? View.VISIBLE : View.GONE;
        labelLayout.setVisibility(visible);
        if (labelVisible) {
            int labelViewId = getLabelViewId(labelType);
            int childCount = labelLayout.getChildCount();
            View visibleView = null;
            for (int i = 0; i < childCount; i++) {
                View child = labelLayout.getChildAt(i);
                if (child.getId() == labelViewId) {
                    child.setVisibility(View.VISIBLE);
                    visibleView = child;
                } else {
                    child.setVisibility(View.GONE);
                }
            }

            if (visibleView != null) {
                int id = visibleView.getId();
                if (id == R.id.numView || id == R.id.textView) {
                    ((TextView) visibleView).setText(label);
                }

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) visibleView.getLayoutParams();
                if (labelMargin != null && labelMargin.length >= 4) {
                    layoutParams.setMargins(labelMargin[3], labelMargin[0], labelMargin[1], labelMargin[2]);
                } else {
                    layoutParams.setMargins(0, 0, 0, 0);
                }
            }
        }
    }
}

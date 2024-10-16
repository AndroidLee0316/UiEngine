package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconTwoTextView extends BaseCardView {

    TextView titleView;
    TextView descView;
    ViewGroup leftTopView;
    ViewGroup topView;
    ViewGroup rightTopView;
    ViewGroup leftView;
    ViewGroup rightView;
    ViewGroup leftBottomView;
    ViewGroup bottomView;
    ViewGroup rightBottomView;
    View textLayout;
    View lineGapView;

    public IconTwoTextView(Context context) {
        super(context);
    }

    public IconTwoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_icon_two_text_view, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
        }

        titleView = findViewById(R.id.title);
        descView = findViewById(R.id.intro);

        topView = findViewById(R.id.topView);
        rightView = findViewById(R.id.rightView);
        rightBottomView = findViewById(R.id.rightBottomView);
        rightTopView = findViewById(R.id.rightTopView);
        leftView = findViewById(R.id.leftView);
        leftTopView = findViewById(R.id.leftTopView);
        leftBottomView = findViewById(R.id.leftBottomView);
        bottomView = findViewById(R.id.bottomView);

        textLayout = findViewById(R.id.textLayout);
        lineGapView = findViewById(R.id.lineGapView);
    }

    @Deprecated
    public void setIconVisible(boolean iconVisible) {
        rightView.setVisibility(iconVisible ? View.VISIBLE : View.GONE);
    }

    @Deprecated
    public void setIconLeftVisible(boolean iconLeftVisible) {
        leftView.setVisibility(iconLeftVisible ? View.VISIBLE : View.GONE);
    }

    public void setTextLayoutPadding(int[] textLayoutPadding) {
        if (textLayoutPadding != null && textLayoutPadding.length >= 4) {
            textLayout.setPadding(textLayoutPadding[3], textLayoutPadding[0], textLayoutPadding[1], textLayoutPadding[2]);
        } else {
            textLayout.setPadding(0, 0, 0, 0);
        }
    }

    private void setViewVisibilityDiff(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    public void setOnlyIconVisible(String iconPosition, boolean iconVisible) {
        setViewVisibilityDiff(rightView, View.GONE);
        setViewVisibilityDiff(leftView, View.GONE);
        setViewVisibilityDiff(rightBottomView, View.GONE);
        setViewVisibilityDiff(leftTopView, View.GONE);
        setViewVisibilityDiff(rightTopView, View.GONE);
        setViewVisibilityDiff(leftBottomView, View.GONE);
        setViewVisibilityDiff(topView, View.GONE);
        setViewVisibilityDiff(bottomView, View.GONE);

        if (iconVisible) {
            setViewVisibilityDiff(getViewGroup(iconPosition), View.VISIBLE);
        }
    }

    public ImageView getIconView(String iconPosition) {
        return getViewGroup(iconPosition).findViewById(R.id.iconView);
    }

    private ViewGroup getViewGroup(String iconPosition) {
        if ("rightBottom".equals(iconPosition)) {
            return rightBottomView;
        } else if ("leftTop".equals(iconPosition)) {
            return leftTopView;
        } else if ("left".equals(iconPosition)) {
            return leftView;
        } else if ("leftBottom".equals(iconPosition)) {
            return leftBottomView;
        } else if ("top".equals(iconPosition)) {
            return topView;
        } else if ("bottom".equals(iconPosition)) {
            return bottomView;
        } else if ("rightTop".equals(iconPosition)) {
            return rightTopView;
        } else {
            return rightView;
        }
    }

    public void setTextLayoutGravity(String textLayoutGravity) {
        LinearLayout textLayout = (LinearLayout) this.textLayout;
        if ("center".equals(textLayoutGravity)) {
            textLayout.setGravity(Gravity.CENTER);
        } else if ("right".equals(textLayoutGravity)) {
            textLayout.setGravity(Gravity.RIGHT);
        } else {
            textLayout.setGravity(Gravity.LEFT);
        }
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

    public void setLabel(String iconPosition, boolean labelVisible, String labelType, String label, int[] labelMargin) {
        int visible = labelVisible ? View.VISIBLE : View.GONE;
        ViewGroup labelLayout = getViewGroup(iconPosition).findViewById(R.id.labelLayout);
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

    public void setIconMargin(String iconPosition, int[] iconMargin) {
        ImageView iconView = getIconView(iconPosition);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iconView.getLayoutParams();
        if (iconMargin != null && iconMargin.length >= 4) {
            layoutParams.setMargins(iconMargin[3], iconMargin[0], iconMargin[1], iconMargin[2]);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
        }
    }

    public View getLineGapView() {
        return lineGapView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDescView() {
        return descView;
    }
}

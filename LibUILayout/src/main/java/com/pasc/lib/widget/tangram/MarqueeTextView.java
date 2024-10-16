package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MarqueeTextView extends BaseCardView {

    private TextView titleView;
    private ImageView closeIcon;
    private ImageView arrowIcon;
    private View iconArea;
    private View iconContainer;

    public MarqueeTextView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lwt_marquee_text_view, this);

        titleView = findViewById(R.id.titleView);
        closeIcon = findViewById(R.id.closeIcon);
        arrowIcon = findViewById(R.id.arrowIcon);
        iconArea = findViewById(R.id.iconArea);
        iconContainer = findViewById(R.id.iconContainer);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public View getIconArea() {
        return iconArea;
    }

    public View getIconContainer() {
        return iconContainer;
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public static enum IconState {
        CLOSE, ARROW, HIDE
    }

    public void setIconState(IconState iconState) {
        switch (iconState) {
            case CLOSE: {
                iconArea.setVisibility(View.VISIBLE);
                closeIcon.setVisibility(View.VISIBLE);
                arrowIcon.setVisibility(View.GONE);
            }
            break;
            case ARROW: {
                iconArea.setVisibility(View.VISIBLE);
                closeIcon.setVisibility(View.GONE);
                arrowIcon.setVisibility(View.VISIBLE);
            }
            break;
            default: {
                iconArea.setVisibility(View.GONE);
                closeIcon.setVisibility(View.GONE);
                arrowIcon.setVisibility(View.GONE);
            }
            break;
        }
    }

    public boolean isCloseState() {
        return closeIcon.getVisibility() == View.VISIBLE;
    }

    public boolean isArrowState() {
        return arrowIcon.getVisibility() == View.VISIBLE;
    }
}

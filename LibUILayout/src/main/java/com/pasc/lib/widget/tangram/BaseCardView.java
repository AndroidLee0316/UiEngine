package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 基本的卡片视图.
 */
public abstract class BaseCardView extends LinearLayout {

    private ArrayList<TextView> textViews; // 文本视图组合
    private float[] defaultTextSizes; // 文本大小
    private ColorStateList[] defaultTextColors; // 文本颜色

    private ArrayList<ImageView> imageViews; // 图片组合
    private int[][] defaultImageSizes; // 默认图片大小

    public BaseCardView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        textViews = new ArrayList<>();
        imageViews = new ArrayList<>();

        initViews(context);

        initDefaultTextViewStyle();
        initDefaultImageViewStyle();
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected abstract void initViews(Context context);

    /**
     * 由于系统的findViewById不能复写，添加此方法，方便获取ID后做一些逻辑处理.
     *
     * @deprecated
     * @param id  视图id.
     * @param <T> 返回的视图对象.
     * @return 返回对应的视图.
     */
    protected <T extends View> T getViewById(@IdRes int id) {
        T view = findViewById(id);
        if (view != null) {
            if (view instanceof TextView) {
                addTextView((TextView) view);
            } else if (view instanceof ImageView) {
                addImageView((ImageView) view);
            }
        }
        return view;
    }

    /**
     * 添加图片视图.
     *
     * @param imageView 图片视图.
     */
    private void addImageView(ImageView imageView) {
        imageViews.add(imageView);
    }

    /**
     * 添加文本视图.
     *
     * @param textView 文本视图.
     */
    private void addTextView(TextView textView) {
        textViews.add(textView);
    }

    /**
     * 初始化文本样式.
     */
    private void initDefaultTextViewStyle() {
        int size = textViews.size();
        defaultTextSizes = new float[size];
        defaultTextColors = new ColorStateList[size];
        for (int i = 0; i < size; i++) {
            TextView textView = textViews.get(i);
            defaultTextSizes[i] = textView.getTextSize();
            defaultTextColors[i] = textView.getTextColors();
        }
    }

    /**
     * 初始化图片样式.
     */
    private void initDefaultImageViewStyle() {
        int size = imageViews.size();
        defaultImageSizes = new int[2][size];
        for (int i = 0; i < size; i++) {
            ViewGroup.LayoutParams layoutParams = imageViews.get(i).getLayoutParams();
            defaultImageSizes[0][i] = layoutParams.width;
            defaultImageSizes[1][i] = layoutParams.height;
        }
    }

    /**
     * 恢复文本大小.
     *
     * @param textView 文本视图.
     */
    public void restoreTextSize(TextView textView) {
        int index = textViews.indexOf(textView);
        if (index != -1) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSizes[index]);
        }
    }

    /**
     * 恢复文本颜色.
     *
     * @param textView 文本视图.
     */
    public void restoreTextColor(TextView textView) {
        int index = textViews.indexOf(textView);
        if (index != -1) {
            textView.setTextColor(defaultTextColors[index]);
        }
    }

    /**
     * 恢复图片大小.
     *
     * @param imageView 图片视图.
     */
    public void restoreImageSize(ImageView imageView) {
        int index = imageViews.indexOf(imageView);
        if (index != -1) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = defaultImageSizes[0][index];
            layoutParams.height = defaultImageSizes[1][index];
        }
    }
}

package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

public class DefaultInnerImageSetter implements IInnerImageSetter {

    private static final String TAG = "LibWidgetTangram";

    @Override
    public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
        if (view == null) return;
        Log.d(TAG, "正在加载图片?url=" + url);
        int width = view.getWidth();
        int height = view.getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int maxWidth = view.getMaxWidth();
        int maxHeight = view.getMaxHeight();
        Log.d(TAG, "正在加载图片?width=" + width + "&height=" + height + "&measuredWidth=" + measuredWidth + "&measuredHeight=" + measuredHeight + "&maxWidth=" + maxWidth + "&maxHeight=" + maxHeight);

        // 如果是res:开头的，则使用本地图片，否则使用url图片
        Integer drawableFromUrl = ConfigUtils.getDrawableFromUrl(view.getContext().getApplicationContext(), url);
        Integer defaultImageDrawableId = (Integer) view.getTag(R.integer.tag_key_default_image);
        if (defaultImageDrawableId == null) {
            defaultImageDrawableId = R.drawable.ic_img_error_44;
        }

        if (drawableFromUrl != null) {
            RequestManager requestManager = Glide.with(view);
            RequestOptions requestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE);
            requestManager.applyDefaultRequestOptions(requestOptions);
            requestManager.load(drawableFromUrl).into(view);
        } else {
            PascImageLoader.getInstance().loadImageUrl(url, view, defaultImageDrawableId, defaultImageDrawableId, PascImageLoader.SCALE_FIT);
        }
    }
}

package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.business.workspace.R;
import com.pasc.business.workspace.widget.event.MarqueeNewsClickEvent;
import com.pasc.lib.widget.PascUpRollView;
import com.pasc.lib.widget.tangram.BaseCardCell;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.tmall.wireless.tangram.MVHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarqueeNewsCell extends BaseCardCell<MarqueeNewsView> {

    public static final String IMG = "img";
    public static final String BG_IMG = "bgImg";
    private static final String TAG = "MarqueeNewsCell";
    private int animInterval;
    private int animDuration;
    private int lineCount;
    private int height;
    private int titleSize;
    private boolean titleLabelVisible;
    private int lineGap;
    private int imgGap;

    @Override
    protected Class getDataSourceType() {
        return InteractionNewsBean.class;
    }

    @Override
    public void parseStyle(@Nullable JSONObject data) {
        super.parseStyle(data);
    }

    @Override
    public void postBindView(@NonNull MarqueeNewsView view) {
        super.postBindView(view);
    }

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        // 动画间隔
        animInterval = optIntParam("animInterval");
        if (animInterval <= 0) {
            animInterval = 3000;
        }
        animInterval = Math.max(animInterval, 1000);
        animInterval = Math.min(animInterval, 10000);

        // 动画时长
        animDuration = optIntParam("animDuration");
        if (animDuration <= 0) {
            animDuration = 1000;
        }
        animDuration = Math.max(animDuration, 500);
        animDuration = Math.min(animDuration, 10000);

        // 行数
        lineCount = optIntParam("lineCount");
        if (lineCount <= 0) {
            lineCount = 2;
        }
        lineCount = Math.max(lineCount, 1);
        lineCount = Math.min(lineCount, 2);

        int heightDp = optIntParam("height");
        if (heightDp <= 0) {
            heightDp = 56;
        }
        height = AndroidUtils.getPixel(heightDp);

        int titleSizeSp = optIntParam("titleSize");
        if (titleSizeSp <= 0) {
            titleSizeSp = 13;
        }
        titleSize = titleSizeSp;

        int lineGapDp = optIntParam("lineGap");
        if (lineGapDp <= 0) {
            lineGapDp = 0;
        }
        lineGap = AndroidUtils.getPixel(lineGapDp);

        int imgGapDp = optIntParam("imgGap");
        if (imgGapDp <= 0) {
            imgGapDp = 6;
        }
        imgGap = AndroidUtils.getPixel(imgGapDp);

        if (hasParam("titleLabelVisible")) {
            titleLabelVisible = optBoolParam("titleLabelVisible");
        } else {
            titleLabelVisible = true;
        }
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    private List dataInside; // 已设置到适配器的数据

    @Override
    protected void bindViewData(final @NonNull MarqueeNewsView marqueeNewsView) {
        super.bindViewData(marqueeNewsView);

        if (marqueeNewsView == null) return;

        final List dataSource = getListDataSource();
        if (dataSource == null || dataSource.size() == 0) {
            marqueeNewsView.getLayoutParams().height = 0;
            marqueeNewsView.setVisibility(View.GONE);
            return;
        } else {
            marqueeNewsView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            marqueeNewsView.setVisibility(View.VISIBLE);
        }

        marqueeNewsView.getRootView().getLayoutParams().height = height;
        marqueeNewsView.setImgGap(imgGap);

        ImageView imgView = marqueeNewsView.getImgView();
        setImageAndStyle(marqueeNewsView, imgView, IMG, null, R.drawable.workspace_marquee_news_two_line_icon);
        setImageAndStyle(marqueeNewsView, marqueeNewsView.getBgImgView(), BG_IMG, null, R.drawable.workspace_bg_marquee_news);

        if (dataInside == dataSource) {
            Log.d(TAG, "数据一致则无须重新设置适配器");
            return;
        }

        final PascUpRollView upRollView = marqueeNewsView.getUpRollView();

        // 设置动画间隔
        upRollView.setRollInterval(animInterval);

        // 设置动画时长
        upRollView.setRollDuration(animDuration);

        Context context = upRollView.getContext();

        int size = dataSource.size();
        Log.d(TAG, "滚动新闻数据量：" + size);
        int pageSize = size / lineCount;
        if (size % lineCount != 0) {
            pageSize++;
        }
        ArrayList<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            ArrayList<String> strings = new ArrayList<>();
            for (int j = 0; j < lineCount; j++) {
                int index = i * lineCount + j;
                if (index < size) {
                    InteractionNewsBean bean = (InteractionNewsBean) dataSource.get(index);
                    String title = bean.getTitle();
                    strings.add(title);
                }
            }
            lists.add(strings);
        }

        PascUpRollView.MultiLineTextAdapter adapter = new PascUpRollView.MultiLineTextAdapter(context, lists) {
            @Override
            public View getView(int position) {
                View view = super.getView(position);
                ViewGroup realContainer = view.findViewById(R.id.realContainer);
                int childCount = realContainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View lineContainer = realContainer.getChildAt(i);
                    View label = lineContainer.findViewById(R.id.upRollPoint);
                    if (label != null) {
                        if (titleLabelVisible) {
                            label.setVisibility(View.VISIBLE);
                        } else {
                            label.setVisibility(View.GONE);
                        }
                    }

                    TextView title = lineContainer.findViewById(R.id.upRollTextView);
                    if (title != null) {
                        title.setTextSize(titleSize);
                    }
                }
                return view;
            }
        };
        adapter.setLineCount(lineCount);
        adapter.setLineGap(lineGap);
        adapter.setLineLayoutId(R.layout.workspace_item_main_news);
        upRollView.setAdapter(adapter);
        dataInside = dataSource;

        upRollView.setOnItemClickListener(new PascUpRollView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MarqueeNewsClickEvent marqueeNewsClickEvent = new MarqueeNewsClickEvent();
                marqueeNewsClickEvent.setMarqueeNewsView(marqueeNewsView);
                marqueeNewsClickEvent.setClickView(view);
                marqueeNewsClickEvent.setClickPosition(position * lineCount);
                marqueeNewsClickEvent.setDataSource(dataInside);
                EventBus.getDefault().post(marqueeNewsClickEvent);
            }
        });
        upRollView.startAutoScroll();
    }
}

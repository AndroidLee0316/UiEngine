package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/8/22
 */
public class BgContentView extends BaseCardView {
    TextView title;
    TextView intro;
    View line;
    public BgContentView(@NonNull Context context) {
        super(context);
//        title = findViewById(R.id.module_title);
        //        intro = findViewById(R.id.intro);
        //        line = findViewById(R.id.module_header_icon_view);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_bg_content, this);
//        title = findViewById(R.id.module_title);
        //        intro = findViewById(R.id.intro);
        //        line = findViewById(R.id.module_header_icon_view);
    }


}

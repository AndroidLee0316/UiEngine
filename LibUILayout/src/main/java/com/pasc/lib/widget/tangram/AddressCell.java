package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.TextView;

import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

public class AddressCell extends BaseCardCell<AddressView> {

    private TextAttr titleAttr;
    private TextAttr descAttr;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        titleAttr = new TextAttr.Builder(data, "title")
                .setDefaultFontSize(15)
                .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
                .setDefaultColor(Color.WHITE)
                .setDefaultBold(false)
                .build();

        descAttr = new TextAttr.Builder(data, "desc")
                .setDefaultFontSize(12)
                .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
                .setDefaultColor(Color.parseColor("#999999"))
                .setDefaultBold(false)
                .build();
    }

    @Override
    protected void bindViewData(@NonNull AddressView view) {
        super.bindViewData(view);

        if (view != null) {
            setText(view.getTitleView(), titleAttr);
            setText(view.getDescView(), descAttr);
        }
    }
}

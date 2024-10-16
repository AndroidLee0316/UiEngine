//package com.pasc.business.life.widget;
//
//import android.support.annotation.NonNull;
//import com.pasc.lib.widget.tangram.BaseCardCell;
//import com.tmall.wireless.tangram.MVHelper;
//import org.json.JSONObject;
//
///**
// * 功能：
// * <p>
// * created by zoujianbo345
// * data : 2018/8/22
// */
//public class BgContyentModel extends BaseCardCell<BgContentView> {
//    private String mTitle;
//    private String mDesc;
//    private boolean isGone;
//
//    @Override
//    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
//        super.parseWith(data, resolver);
//
//        mTitle = data.optString("title");
//        mDesc = data.optString("desc");
//        isGone = data.optBoolean("isGone");
//    }
//
//    @Override
//    protected void bindViewData(@NonNull BgContentView view) {
//        super.bindViewData(view);
////        if (isGone) {
////            view.line.setVisibility(View.GONE);
////        }
//    }
//}

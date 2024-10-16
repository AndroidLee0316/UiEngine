//package com.pasc.business.life.widget;
//
//import android.support.annotation.NonNull;
//import com.google.gson.Gson;
//import com.pasc.lib.log.PascLog;
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
//public class FourTxtModel extends BaseCardCell<FourTxtView> {
//
//    private float changePersent;//挂牌均价
//
//    private String price;//均价
//
//    private String dealCount;//成交套数
//
//    @Override
//    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
//        super.parseWith(data, resolver);
//
//        JSONObject dataSrc = data.optJSONObject("data");
//        Gson gson = new Gson();
//        try {
//            if (dataSrc != null) {
//                price = dataSrc.getString("price");
//                dealCount = dataSrc.getString("dealCount");
//                changePersent = Float.valueOf(dataSrc.getString("changePersent"));
//
//            }
//        } catch (Exception e) {
//            PascLog.e(e.toString());
//        }
//    }
//
//    @Override
//    protected void bindViewData(@NonNull FourTxtView view) {
//        super.bindViewData(view);
//
//        if(price != null && dealCount != null) {
//            view.setAveragePrice(price);
//            view.setSaleAmount(dealCount);
//            if (changePersent >= 0) {
//                view.setRelativeRatio("+" + changePersent + "%", true);
//            } else {
//                view.setRelativeRatio(changePersent + "%", false);
//            }
//        }
//    }
//}

//package com.pasc.business.life.widget;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//import com.pasc.lib.life.R;
//import com.pasc.lib.widget.tangram.BaseCardView;
//
///**
// * 功能：
// * <p>
// * created by zoujianbo345
// * data : 2018/8/22
// */
//public class FourTxtView extends BaseCardView {
//    TextView mAveragePrice;
//    TextView mRelativeRatio;
//    TextView mSaleAmount;
////    TextView intro;
////    View line;
//    public FourTxtView(@NonNull Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void initViews(Context context) {
//        View houseInfView = LayoutInflater.from(context).inflate(R.layout.item_four_txt, this);
//        mAveragePrice = houseInfView.findViewById(R.id.tv_four);
//        mRelativeRatio = houseInfView.findViewById(R.id.tv_two);
//        mSaleAmount = houseInfView.findViewById(R.id.tv_five);
//    }
//
//
//
//
//    public void setAveragePrice(String text){
//        mAveragePrice.setText(text);
//    }
//
//    public void setRelativeRatio(String text , boolean isPositive){
//        mRelativeRatio.setText(text);
//        if(isPositive){
//            mRelativeRatio.setTextColor(getResources().getColor(R.color.red_FF7878));
//        }else{
//            mRelativeRatio.setTextColor(getResources().getColor(R.color.green_18C69F));
//        }
//    }
//
//    public void setSaleAmount(String text){
//        mSaleAmount.setText(text);
//    }
//
//
//
//
//
//}

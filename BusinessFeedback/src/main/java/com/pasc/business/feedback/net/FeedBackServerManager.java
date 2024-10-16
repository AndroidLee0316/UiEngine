package com.pasc.business.feedback.net;

import android.util.Log;

import com.pasc.lib.net.ExceptionHandler;
import com.pasc.lib.net.resp.VoidObject;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/8/16
 */
public class FeedBackServerManager {
    private static final String TAG = "FeedBackServerManager";

    public static void feedBack(ArrayList<String> imagePathList, final String phoneSystemType, final String phoneModel, final String appVersion, final String message, final FeedCallBack feedCallBack){
        FeedBackBiz.feedBack(imagePathList, phoneSystemType, phoneModel, appVersion, message).subscribe(new SingleObserver<VoidObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(VoidObject voidObject) {
                if (feedCallBack != null){
                    feedCallBack.onSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

                if (feedCallBack != null){
                    feedCallBack.onFailed(ExceptionHandler.handleException(e));
                }
            }
        });
    }


    public static void feedBack (ArrayList<String> imagePathList, final String userDefineType,
                                 final String phoneSystemType, final String phoneModel, final
                                 String appVersion, final String message, final FeedCallBack
                                         feedCallBack) {
        FeedBackBiz.feedBackCS(imagePathList, userDefineType, phoneSystemType, phoneModel,
                appVersion, message).subscribe(new SingleObserver<VoidObject>() {
            @Override
            public void onSubscribe (Disposable d) {

            }

            @Override
            public void onSuccess (VoidObject voidObject) {
                if (feedCallBack != null){
                    feedCallBack.onSuccess();
                }
            }

            @Override
            public void onError (Throwable e) {
                Log.e(TAG, "onError: "+e.getMessage());
                if (feedCallBack != null){
                    feedCallBack.onFailed(ExceptionHandler.handleException(e));
                }
            }
        });
    }
    public static interface FeedCallBack{
        void onSuccess();

        void onFailed(String msg);
    }

}

package com.pasc.business.feedback.net;

import android.os.UserManager;

import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.BitmapUtils;
import com.pasc.lib.base.util.FileUtils;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.net.transform.RespTransformer;
import com.pasc.lib.net.transform.RespV2Transformer;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/8/16
 */
public class FeedBackBiz {

    public static Single<VoidObject> feedBack(ArrayList<String> imagePathList, final String phoneSystemType, final String phoneModel, final String appVersion, final String message){
        return Flowable.fromArray(imagePathList)
                .flatMap(new Function<ArrayList<String>, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(ArrayList<String> strings)
                            throws Exception {
                        return Flowable.fromIterable(strings);
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {

                    @Override
                    public String apply(String picturePath) throws Exception {
                        String[] split = picturePath.split("/");
                        String destPicturePath =
                                FileUtils.getImgFolderPath() + split[split.length - 1];
                        BitmapUtils.compressPhoto(picturePath, destPicturePath, 300, 1);
                        return destPicturePath;
                    }
                })
                .flatMap(new Function<String, Publisher<String>>() {

                    @Override
                    public Publisher<String> apply(String s) throws Exception {
                        return uploadFeedbackImage(s).toFlowable();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String feedBackImageResp)
                            throws Exception {

                        return feedBackImageResp;
                    }
                })
                .toList()
                .flatMap(new Function<List<String>, SingleSource<VoidObject>>() {
                    @Override
                    public SingleSource<VoidObject> apply(List<String> strings)
                            throws Exception {
                        return addFeedBack(phoneSystemType, phoneModel, appVersion,
                                message, strings);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<VoidObject> feedBackCS (ArrayList<String> imagePathList, final String
            userDefineType, final String phoneSystemType, final String phoneModel, final String
                                                       appVersion, final String message) {
        return Flowable.fromArray(imagePathList)
                .flatMap(new Function<ArrayList<String>, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply (ArrayList<String> strings)
                            throws Exception {
                        return Flowable.fromIterable(strings);
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {

                    @Override
                    public String apply (String picturePath) throws Exception {
                        String[] split = picturePath.split("/");
                        String destPicturePath =
                                FileUtils.getImgFolderPath() + split[split.length - 1];
                        BitmapUtils.compressPhoto(picturePath, destPicturePath, 300, 1);
                        return destPicturePath;
                    }
                })
                .flatMap(new Function<String, Publisher<String>>() {

                    @Override
                    public Publisher<String> apply (String s) throws Exception {
                        return uploadFeedbackImage(s).toFlowable();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply (String imageUrl)
                            throws Exception {
                        return imageUrl;
                    }
                })
                .toList()
                .flatMap(new Function<List<String>, SingleSource<VoidObject>>() {
                    @Override
                    public SingleSource<VoidObject> apply (List<String> strings)
                            throws Exception {
                        return addFeedBackCS(userDefineType, phoneSystemType, phoneModel, appVersion,
                                message, strings);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }



    /**
     * @param imagesPath 图片文件路径
     */
    public static Single<String> uploadFeedbackImage(String imagesPath) {
        File file = new File(imagesPath);
        RespV2Transformer<String> respTransformer = RespV2Transformer.newInstance();
        String type = "image/jpeg";
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        return ApiGenerator.createApi(FeedBackApi.class)
                .uploadFeedbackImage(FeedBackManager.getApiGet().getFeedbackImageUrl(),FeedBackManager.getApiGet().getToken(),type,filePart)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param phoneSystemType 系统版本
     * @param phoneModel      系统型号
     * @param appVersion      app版本号
     * @param question        意见
     * @param imagesUrlList   图片Url数组
     */
    public static Single<VoidObject> addFeedBack(String phoneSystemType, String phoneModel,
                                                 String appVersion, String question, List<String> imagesUrlList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < imagesUrlList.size(); i++){
            stringBuffer.append("\'").append(imagesUrlList.get(i)).append("\'");
            if (imagesUrlList.size() -1 != i){
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");

        FeedBackParam param = new FeedBackParam(phoneSystemType, phoneModel,
            appVersion, question,
            stringBuffer.toString());
        RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();

        return ApiGenerator.createApi(FeedBackApi.class)
            .addFeedBack(FeedBackManager.getApiGet().getFeedbackUrl(),
                    FeedBackManager.getApiGet().getToken(), param)
             .compose(respTransformer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * @param phoneSystemType 系统版本
     * @param phoneModel      系统型号
     * @param appVersion      app版本号
     * @param question        意见
     * @param imagesUrlList   图片Url数组
     */
    public static Single<VoidObject> addFeedBackCS (String userDefineType, String phoneSystemType,
                                                  String phoneModel,
                                                  String appVersion, String question,
                                                  List<String> imagesUrlList) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < imagesUrlList.size(); i++){
            stringBuffer.append("\'").append(imagesUrlList.get(i)).append("\'");
            if (imagesUrlList.size() -1 != i){
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");

        FeedBackParam param = new FeedBackParam(userDefineType, phoneSystemType, phoneModel,
                appVersion, question,
                stringBuffer.toString());
        RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();

        return ApiGenerator.createApi(FeedBackApi.class)
                .addFeedBack(FeedBackManager.getApiGet().getFeedbackUrl(),
                        FeedBackManager.getApiGet().getToken(), param)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

package com.pasc.demo.widget.tangram;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiUnitTest {

    /*
     * 测试获取banner接口.
     */
    @Test
    public void testGetBanner() throws IOException {
        BannerParam bannerParam = new BannerParam();
        bannerParam.setBannerLocation(0);
        bannerParam.setShowType(1);
        bannerParam.setVersionCode(101);
        bannerParam.setVersionNo("1.0.1");

        String bannerParams = new Gson().toJson(bannerParam);
        System.out.println("请求参数：" + bannerParams);

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), bannerParams);
        System.out.println("参数类型：" + requestBody.contentType());
        String url = "http://cssc-smt-stg5.yun.city.pingan.com/api/platform/homePage/queryAppBannerListInfo";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        okhttp3.Response execute = okHttpClient.newCall(request).execute();
        System.out.println("返回结果：" + execute);
        System.out.println("返回内容：" + execute.body().string());
    }
}
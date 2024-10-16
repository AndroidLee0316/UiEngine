package com.pasc.lib.workspace.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.net.ApiError;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.bean.NtErrorCode;

import java.io.IOException;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 业务工具，处理业务时使用.
 * Created by chenruihan410 on 2018/09/10.
 */
public class BizUtils {

    public static final String getTestFlag() {
        String testFlag = AppProxy.getInstance().isProductionEvn() ? "0" : "1"; // 0代表生产环境，1代表测试环境
        return testFlag;
    }

    public static String getCacheKeyConfig(Context context, String configId, String phoneNumber) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        long versionCode = AppUtils.getVersionCode(context);
        String testFlag = getTestFlag();
        String packageName = context.getPackageName();
        params.put("packageName", packageName);
        params.put("testFlag", testFlag);
        params.put("phoneNumber", phoneNumber);
        params.put("versionCode", String.valueOf(versionCode));
        params.put("configId", configId);
        return CacheUtils.getCacheKey("BusinessWorkspace", "config", params);
    }

    @NonNull
    public static String getCacheKeyScrollNews() {
        return BusinessConstants.SCROLL_NEWS + InteractionNewsBean.SourceType.FROM_MAIN_PAGE_SCROLL_NEWS_TYPE;
    }

    @NonNull
    public static String getCacheKeyNews() {
        return "News";
    }

    public static String getCacheKeyTodayWater() {
        return BusinessConstants.MAIN_PAGE_TODAY_WATER_QUALITY_INFO;
    }

    private static <T> void checkResponse(Response<BaseResp<T>> response) {
        if (response == null) {
            throw new RuntimeException("网络异常");
        }
        if (!response.isSuccessful()) {
            throw new ApiError(response.code(), "接口异常");
        }
        BaseResp<?> baseResp = response.body();
        if (baseResp == null) {
            throw new ApiError(NtErrorCode.ERROR, "接口无返回数据");
        }
        if (!NtErrorCode.isSuccess(baseResp.code)) {
            throw new ApiError(baseResp.code, baseResp.msg);
        }
        if (baseResp.data == null) {
            throw new ApiError(NtErrorCode.ERROR, "接口无返回数据");
        }
    }

    private static <T> T getResponseData(Response<BaseResp<T>> response) {
        checkResponse(response);
        BaseResp<T> body = response.body();
        return body.data;
    }

    public static <T> T getNetData(Call<BaseResp<T>> call) {
        Response<BaseResp<T>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("网络发生异常");
        }
        return getResponseData(response);
    }

    /**
     * 检查网络环境.
     *
     * @param context 上下文.
     */
    public static void checkNetwork(Context context) {
        if (!CommonUtils.isNetworkAvailable(context)) {
            throw new ApiError(NtErrorCode.ERROR, "当前网络异常，请检查一下");
        }
    }

    public static String getCacheKeyHouseSecurity() {
        return BusinessConstants.HOME_HOUSE_SECURITY;
    }

    public static String getCacheKeyReadAnnouncement(String id) {
        return "ReadAnnouncement" + id;
    }
}

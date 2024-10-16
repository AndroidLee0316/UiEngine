package com.pasc.business.feedback.net;

import com.pasc.lib.base.AppProxy;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/8/24
 * @des
 * @modify
 */
public class FeedBackManager {
    private static FeedBackManager.ApiGet apiGet;

    //意见反馈附件
    public static final String FEED_BACK_IMAGE = "api/platform/feedback/uploadFeedbackImage";
    //意见反馈
    public static final String ADD_FEED_BACK =  "api/platform/feedback/submitFeedback";

    //意见反馈附件 常熟
    public static final String FEED_BACK_IMAGE_CS = "platform/feedback/uploadFeedbackImage";

    //意见反馈  常熟
    public static final String ADD_FEED_BACK_CS =  "platform/feedback/submitFeedback";



    public static ApiGet getApiGet() {
        return apiGet == null?new ApiGet() {
            @Override
            public String getFeedbackImageUrl() {
                return AppProxy.getInstance().getHost() + "/"+FEED_BACK_IMAGE;
            }

            @Override
            public String getFeedbackUrl() {
                return AppProxy.getInstance().getHost() + "/"+ADD_FEED_BACK;
            }

            @Override
            public String getToken() {
                return AppProxy.getInstance().getUserManager().getToken();
            }


        }:apiGet;
    }

    public static void setApiGet(ApiGet apiGets) {
        apiGet = apiGets;
    }

    public abstract static class ApiGet {
        public ApiGet() {
        }
        public abstract String getFeedbackImageUrl();
        public abstract String getFeedbackUrl();
        public abstract String getToken();

    }




}

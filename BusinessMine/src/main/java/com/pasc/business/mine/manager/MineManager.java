package com.pasc.business.mine.manager;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/8/24
 * @des
 * @modify
 */
public class MineManager {

    // 用户头像上传
    public static final String USER_HEAD_IMG_UPLOAD = "api/auth/user/userHeadImg";
    //修改用户信息
    public static final String MODIFY_USER_MSG = "api/auth/user/updateUserInfo";

    //获取省市区地址列表
    public static final String AREA_LIST = "api/auth/area/getOrgAreaList";
    //新增用户收货地址
    public static final String NEW_ADDRESS = "api/auth/address/addNewUserAddress";
    //获取收货地址列表
    public static final String GET_ADDRESS_LIST = "api/auth/address/getUserAddrByUserId";
    //修改收货地址
    public static final String UPDATE_ADDRESS = "api/auth/address/updateUserAddress";
    //删除收货地址
    public static final String DELETE_ADDRESS = "api/auth/address/deleteUserAddress";
    //设置默认地址
    public static final String SET_DEFAULT_ADDRESS = "api/auth/address/setToDefaultAddress";
    //获取实名认证方式
    public static final String QUERY_REALNAME_TYPE = "nantongsmt/user/realNameAuth/queryRealNameType.do";

    //政民互动收藏列表
    public static final String COLLECTION_INTERACTION_NEWS =  "nantongsmt/userCollect/showCollectList.do";
    // 信用查询
    public static final String CREDIT_QUERY =  "api/platform/qianhai/myCredit";
    //个人中心获取 收藏 业务 消息数量
    public static final String USER_MSG_NUM = "nantongsmt/userInfo/userInfoCSMRecordNum.do";
    //我的卡包列表
    public static final String USER_MY_CARD_LIST = "nantongsmt/cardBag/getCardBag.do";
    //根据手机号获取用户信息
    public static final String IS_OPEN_FACE = "/platform/pinganFace/getOpenfaceStateByMobile";
    //修改人脸登录开关
    public static final String UPDATE_OPEN_FACE = "/platform/pinganFace/updateOpenFace";
    //验证新手机号码是否已注册
    public static final String VERIFY_PHONE_REGISTRATION = "/platform/user/checkMobileExsits";
    //更改手机号
    public static final String CHANGE_PHONE = "/platform/user/rebindingMobile";
    //根据姓名身份证号码找回账号
    public static final String RETRIEVE_ACCOUNT = "platform/account/validateIdNoAndName";
    //找回账号修改手机号码
    public static final String BINDING_NEW_PHONE = "platform/account/mobileRegistered";

    private MineManager() {
    }

    public static MineManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init() {
        initMineDb();
    }

    private void initMineDb() {
    }


    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final MineManager INSTANCE = new MineManager();
    }


}

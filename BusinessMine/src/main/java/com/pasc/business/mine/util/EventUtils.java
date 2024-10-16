package com.pasc.business.mine.util;

import android.content.Context;

import com.pasc.lib.statistics.StatisticsManager;

import java.util.Map;

/**
 * 天眼事件统计工具类
 * Created by duyuan797 on 18/2/5.
 */

public class EventUtils {

    //==========================================Event ID============================================
    //01首页
    public static final String E_MAIN_PAGE = "01首页";

    //02个人中心
    public static final String E_PERSONAL_INFO = "person_info";

    public static final String E_LOGIN = "用户登录";
    public static final String E_FORGET_PASSWORD = "040102忘记密码";
    public static final String E_SIGNUP = "用户注册";
    public static final String E_BIND_PHONE = "04010104绑定手机号";
    public static final String E_MESSAGE_NOTICE = "0402消息通知";
    public static final String E_SETTING = "0403设置";
    public static final String E_SETTING_SHARE = "040304设置-分享";
    public static final String E_SETTING_USER_DATA = "040301设置-用户资料";
    public static final String E_USER_DATA_AVATAR = "040301用户资料-头像";
    public static final String E_USER_DATA_NAME = "040301用户资料-用户名";
    public static final String E_USER_DATA_GENDER = "040301用户资料-性别";
    public static final String E_USER_DATA_IDCARD = "040301用户资料-身份证号码";
    public static final String E_USER_DATA_ADDRESS = "040301用户资料-联系地址";
    public static final String E_SETTING_SECURITY = "040302设置-账户安全";
    public static final String E_SECURITY_MODIFY_PASSWORD = "040302账户安全-修改登录密码";
    public static final String E_MODIFY_PASSWORD_FORGET_PASSWORD = "040302修改登录密码-忘记原密码";
    public static final String E_SECURITY_ACCOUNT_VERIFY = "040302账户安全-账户验证";
    public static final String E_SECURITY_LOGIN_BY_FACE = "040302账户安全-人脸登录";
    public static final String E_SECURITY_LOGIN_BY_FACE_IS_NONACTIVATED = "040302账户安全-人脸登录未开通";
    public static final String E_LOGIN_BY_FACE_RESET_PHOTO = "040302人脸登录已开通-重置人脸照片";
    public static final String E_SUGGESTION_FEEDBACK = "0405-意见反馈";
    public static final String E_SUGGESTION_FEEDBACK_UPLOAD_PICTURE = "040501意见反馈-上传图片";
    public static final String E_SUGGESTION_FEEDBACK_SELECT_FROM_ALBUM = "04050101意见反馈-从相册选择";

    public static final String E_CERTIFICATION_CHOOSE = "身份认证";
    public static final String E_CERTIFICATION_BANK = "040301银行卡认证";
    public static final String E_CERTIFICATION_BANK_MESSAGE = "040301银行卡认证-短信验证";
    public static final String E_CERTIFICATION_BANK_MESSAGE_PASS = "银行卡认证-短信验证-通过提示";
    public static final String E_CERTIFICATION_BANK_MESSAGE_PASS_FAIL = "银行卡认证-短信验证-失败提示";



    //===========================================Label ID===========================================

    //01个人中心
    public static final String L_CARD = "我的卡包";
    public static final String L_SERVICE = "我的服务";
    public static final String L_SERVICE_1 = "我的办事";
    public static final String L_SERVICE_2 = "我的预约";
    public static final String L_SERVICE_3 = "我的缴费";
    public static final String L_SERVICE_4 = "我的收藏";
    public static final String L_SHARE = "推荐app分享";
    public static final String L_USER_DATA = "用户资料";
    public static final String L_LOGIN_OR_SIGNUP = "登录/注册";
    public static final String L_CERTIFICAION = "认证";
    public static final String L_SETTING = "设置";
    public static final String L_GO_TO_PRAISE = "去好评";
    public static final String L_ABOUT = "关于我们";
    public static final String L_ADDRESS = "联系地址";
    public static final String L_ADD_ADDRESS = "添加地址";
    public static final String L_EDIT_ADDRESS = "编辑联系地址";

    public static final String L_TAKE_PHOTOS = "拍照";
    public static final String L_SELECT_IN_ALBUM = "从相册选择";

    public static final String L_LOGIN_BY_FACE = "人脸登录";
    public static final String L_LOGIN_BY_FACE_FAILED_RELOGIN = "人脸登录失败重新登录";
    public static final String L_CLOSE_LOGINING_BY_FACE = "关闭人脸登录";
    public static final String L_MESSAGE_NOTICE = "消息通知";
    public static final String L_MY_OFFICE = "我的办事";
    public static final String L_LOGIN_BY_PASSWORD = "密码登录";
    public static final String L_LOGIN_BY_SMS = "短信登录";
    public static final String L_GET_CAPTCHA = "获取验证码";
    public static final String L_SMS_CAPTCHA = "短信验证码";
    public static final String L_FORGET_PASSWORD = "忘记密码";
    public static final String L_MODIFY_PASSWORD = "修改登录密码";
    public static final String L_SIGNUP = "用户注册";
    public static final String L_QUIT_RECORDING_FACE = "退出录脸";
    public static final String L_PHONE_NUMBER = "手机号";
    public static final String L_SEND_CAPTCHA_SUCCESSFULLY = "发送验证码成功";
    public static final String L_SEND_CAPTCHA_UNSUCCESSFULLY = "发送验证码失败";
    public static final String L_QUIT_SINGUP = "退出注册";
    public static final String L_BUSINESS_NOTICE = "业务通知";
    public static final String L_OTHER_NOTICE = "其他通知";
    public static final String L_ACCOUNT_SECURITY = "账户安全";
    public static final String L_CREDIT_REPORT = "信用报告";
    public static final String L_ADD_PICTURES = "添加图片";
    public static final String L_DELETE_ADDED_PICTURES = "删除添加的图片";

    public static final String L_SELECT_THE_PHOTO_IN_PREVIEW = "预览状态下勾选照片";
    public static final String L_SELECT_THE_PHOTO_IN_ALL_LIST = "所有照片状态下勾选照片";
    public static final String L_FINISH_THE_PHOTO_SELECTION = "完成";
    public static final String L_MALE = "男";
    public static final String L_FEMALE = "女";

    public static final String L_USER_AGREEMENT = "用户协议";


    public static final String L_MY_CARDBAG = "我的卡包";
    public static final String L_OPININ_FEEDBACK = "意见反馈";
    public static final String L_FACE_CERTIFICAION = "扫脸认证";
    public static final String L_BANK_CERTIFICAION = "银行卡认证";


    //==========================================KEY===========================================

    public static final String KEY_SETING = "setting";
    public static final String KEY_SERVICE = "service";
    public static final String KEY_USER_DATA = "用户资料";


    public static final String KEY_USER_NAME = "nick_name";
    public static final String KEY_GENDER = "user_gender";
    public static final String KEY_IDCARD = "user_idcard";
    public static final String KEY_AVATAR = "user_avatar";
    public static final String KEY_ADDRESS = "user_address";
    public static final String KEY_TAKE_PHOTOS = "take_photos";
    public static final String KEY_SELECT_IN_ALBUM = "select_in_album";

    //==========================================VALUE===========================================
    public static final String VALUE_USER_DATA = L_USER_DATA;
    public static final String VALUE_SETTING_ABOUT = "设置-关于";
    public static final String VALUE_CLEAR_CACHE = "清除缓存";
    public static final String VALUE_QUIT_LOGIN = "退出登录";
    public static final String VALUE_CERTIFICAION = "账户安全";
    public static final String VALUE_FEEDBACK = "意见反馈";

    public static final String VALUE_USER_NAME = "用户昵称";
    public static final String VALUE_GENDER = "性别";
    public static final String VALUE_IDCARD = "身份证号码";
    public static final String VALUE_AVATAR = "头像";
    public static final String VALUE_ADDRESS = "联系地址";
    public static final String VALUE_TAKE_PHOTOS = L_TAKE_PHOTOS;
    public static final String VALUE_SELECT_IN_ALBUM = L_SELECT_IN_ALBUM;


    public static void onEvent(String eventId, String label, Map map) {
        if (map != null) {
            StatisticsManager.getInstance().onEvent( eventId, label, map);
        } else {
            onEvent(eventId, label);
        }
    }

    public static void onEvent(String eventId, String label) {
        StatisticsManager.getInstance().onEvent( eventId, label);
    }

    public static void onEvent(String eventId) {
        StatisticsManager.getInstance().onEvent(eventId);
    }

    public static void onResume(Context context) {
        StatisticsManager.getInstance().onResume(context);
    }

    public static void onPause(Context context) {
        StatisticsManager.getInstance().onPause(context);
    }
}

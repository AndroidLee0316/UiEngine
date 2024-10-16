package com.pasc.business.mine.server;

/**
 * @author yangzijian
 * @date 2018/9/29
 * @des
 * @modify
 **/
public class UserConfig {

    //获取省市区地址列表
    public static final String AREA_LIST = "nantongsmt/orgArea/getOrgAreaList.do";

    //新增用户收货地址
    public static final String NEW_ADDRESS = "nantongsmt/userAddress/addNewUserAddress.do";
    //获取收货地址列表
    public static final String GET_ADDRESS_LIST = "nantongsmt/userAddress/getUserAddrByUserId.do";
    //修改收货地址
    public static final String UPDATE_ADDRESS = "nantongsmt/userAddress/updateUserAddress.do";
    //删除收货地址
    public static final String DELETE_ADDRESS = "nantongsmt/userAddress/deleteUserAddress.do";
    //设置默认地址
    public static final String SET_DEFAULT_ADDRESS = "nantongsmt/userAddress/setToDefaultAddress.do";
    //获取实名认证方式
    public static final String QUERY_REALNAME_TYPE = "nantongsmt/user/realNameAuth/queryRealNameType.do";

}

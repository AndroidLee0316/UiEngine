package com.pasc.business.mine.server;


/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/10/19
 */
public class AddressServerType {


        public static AddressServerManagerInterface getCS(){
            return new CSAddressServerManager();
        }



        public static AddressServerManagerInterface getDefault(){
            return getCS();
        }


    }

package com.pasc.pascuiengine.event;


public class EventTable {

    /***用户***/
    public static class User{
        public static final String user_certificate_not_tag = "user_certificate_not";// 未认证
        public static final String user_from_exit_tag = "user_from_exit";//用户退出
        public static final String user_invalid_token_tag = "user_invalid_token";//token失效
        public static final String user_login_succeed_tag= "user_login_succeed";//登录成功

        public static final String user_kickoff_tag = "user_kickoff_tag";// 用户被踢
        public static final String user_login_status_tag="user_login_status";
        public static final String user_login_status_out_value="user_login_status_out_value";
        public static final String user_login_status_in_value="user_login_status_in_value";


    }
    /***消息***/
    public static class Message{

        /***发送消息数量事件***/
        public static final String message_show_num_tag="message_show_num_tag";
        /***发送消息数量事件 map 的 key   String num=BaseEvent.getParams().get(message_num_key)***/
        public static final String message_num_key="message_num_key";

        /*****刷新消息数据******/
        public static final String message_refresh_num_tag="message_refresh_num_tag";

    }
}

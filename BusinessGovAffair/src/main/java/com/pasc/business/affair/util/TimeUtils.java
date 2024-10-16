package com.pasc.business.affair.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/18
 */
public class TimeUtils {

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @param type
     * @return
     */
    public static String timeToString (String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type,
                Locale.CHINA);
        Date date;
        String times = null;
        try{
            long l = Long.valueOf(time).longValue();
            date = new Date(l);
            SimpleDateFormat formatter = new SimpleDateFormat(type);
            times = formatter.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String timeToString (String time) {
        return timeToString(time, "yyyy-MM-dd");
    }

    /**
     * 格式化
     *
     * @param time millisecond
     * @return
     */
    public static String getFormatTime (long time) {
        long diff = System.currentTimeMillis() - time;

        //小于一天的
        if (diff < 24 * 60 * 60 * 1000){
            StringBuilder stringBuilder = new StringBuilder();
            //获取时间节点
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            int d = c.get(Calendar.DAY_OF_YEAR);

            Calendar c_ = Calendar.getInstance();
            c_.setTimeInMillis(System.currentTimeMillis());
            int h_ = c_.get(Calendar.HOUR_OF_DAY);
            int m_ = c_.get(Calendar.MINUTE);
            int d_ = c_.get(Calendar.DAY_OF_YEAR);


            int h_diff = h_ - h;
            int m_diff = m_ - m;
            int d_diff = d_ - d;



            if (h_diff > 0){
                //时间节点，没有在24点前后
                stringBuilder.append(h_diff+"小时");
            } else if (h_diff == 0){
                if (d_diff > 0){
                    //第二天的时候
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    Date date = new Date(time);
                    return formatter.format(date);
                }
            }else {
                //时间节点，在24点前后
                h_diff = h_ + 24 - h;
                stringBuilder.append(h_diff+"小时");
            }

            if (m_diff > 0){
                //时间节点，没有在60分前后
                stringBuilder.append(m_diff+"分钟前");
            } else if (m_diff == 0){
                stringBuilder.append("1分钟前");
            }else {
                //时间节点，在60分前后
                m_diff = m_ + 60 - m;
                stringBuilder.append(m_diff+"分钟前");

                if (h_diff > 0){
                    //需要倒退一小时
                    stringBuilder.delete(0, stringBuilder.length());
                    if (h_diff - 1 > 0){
                        stringBuilder.append(h_diff-1+"小时").append(m_diff+"分钟前");
                    }else {
                        stringBuilder.append(m_diff+"分钟前");
                    }

                }
            }

            return stringBuilder.toString();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date = new Date(time);

        return  formatter.format(date);
    }

    /**
     * 格式化
     *  今天的消息：今天 12:00
     *  昨天的消息：昨天 12:00
     *  今年的消息：11-11 12:00
     *  其      他：2018-11-11 12:00
     * @param time millisecond
     * @return
     */
    public static String getFormatTime2 (long time) {
        //获取时间节点
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int y = c.get(Calendar.YEAR);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        int d = c.get(Calendar.DAY_OF_YEAR);

        Calendar c_ = Calendar.getInstance();
        c_.setTimeInMillis(System.currentTimeMillis());
        int y_ = c_.get(Calendar.YEAR);
        int d_ = c_.get(Calendar.DAY_OF_YEAR);

        int y_diff = y_ - y;
        int d_diff = d_ - d;

        SimpleDateFormat formatter;
        if (y_diff == 0){
            StringBuilder stringBuilder = new StringBuilder();
            //今年
            if (d_diff == 0){
                //同一天
                stringBuilder.append("今天 ").append(h).append(":");
                if (m < 10){
                    stringBuilder.append("0");
                }
                stringBuilder.append(m);
                return stringBuilder.toString();
            } else if (d_diff == 1){
                //昨天
                stringBuilder.append("昨天 ").append(h).append(":");
                if (m < 10){
                    stringBuilder.append("0");
                }
                stringBuilder.append(m);
                return stringBuilder.toString();
            }else {
                formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
            }
        }else {
            //其他
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        }
        Date date = new Date(time);

        return  formatter.format(date);
    }
}

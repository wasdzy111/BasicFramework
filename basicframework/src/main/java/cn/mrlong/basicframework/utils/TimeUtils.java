package cn.mrlong.basicframework.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly343 on 2018/6/29.
 */

public class TimeUtils {

    /**
     * 格式化时间
     *
     * @param millTime
     * @param format
     * @return
     */
    public static String getFormatTime(long millTime, String format) {
        String formatTime = "";
        if (millTime > 0) {

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date();
            date.setTime(millTime);
            formatTime = sdf.format(date);
        }
        return formatTime;
    }

    /**
     * 格式化2017-07-17 13:42:42这种格式
     *
     * @param millTime
     * @return
     */
    public static String getFormatTime(long millTime) {
        //2017-07-17 13:42:42
        return getFormatTime(millTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime(String millTime) {
        //2017-07-17 13:42:42
        return getFormatTime(Long.parseLong(millTime), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime2(String millTime) {
        //2017-07-17 13:42
        return getFormatTime(Long.parseLong(millTime), "yyyy-MM-dd HH:mm");
    }

    public static String getFormatTime4(String millTime) {
        //2017-07-17 13:42
        return getFormatTime(Long.parseLong(millTime), "MM-dd HH:mm:ss");
    }

    /**
     * 获取今天的的日期 时间
     *
     * @return
     */
    public static String getCurrentDate() {
        //2017-07-17 13:42:42
        return getFormatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取今天的的日期
     *
     * @return
     */
    public static String getCurrentDate2() {
        //2017-07-17 13:42:42
        return getFormatTime(System.currentTimeMillis(), "yyyy-MM-dd");
    }

    /**
     * 获取今天的的日期
     *
     * @return
     */
    public static String getCurrentDate3() {
        //2017-07-17 13:42:42
        String date = getFormatTime(System.currentTimeMillis(), "yyyy-MM-dd");
        String time = "23:59:59";
        return date + " " + time;
    }

    /**
     * 获取昨天
     *
     * @param i 当i为正数的时候 为明天及前退 为负数的时候为昨天及后退
     */
    public static String getDate(int i) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        String dateString = formatter.format(date);
        return dateString + "00:00:00";
    }

    /**
     * 设置当前时间
     *
     * @param cxt
     * @param datetimes
     */
    public static void setSystemTime(final Context cxt, String datetimes) {

        try {
            Process process = Runtime.getRuntime().exec("su");
//          String datetime = "20131023.112800"; // 测试的设置的时间【时间格式
            //String datetime = ""; // 测试的设置的时间【时间格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd.HHmmss");
            String datetime = formatter.format(datetimes);
            //datetime = datetimes.toString(); // yyyyMMdd.HHmmss】
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            os.writeBytes("setprop persist.sys.timezone GMT\n");
            os.writeBytes("/system/bin/date -s " + datetime + "\n");
            os.writeBytes("clock -w\n");
            os.writeBytes("exit\n");
            os.flush();
            Toast.makeText(cxt, "设置时间成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(cxt, "请获取Root权限", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置安卓系统时间
     *
     * @param context
     */
    public static void setCustomSystemTime(Context context, String longTime) {
        Map<String, String> map = getFormatTime3(longTime);
        if (map.size() == 0) {
            return;
        }

        Intent mIntent = new Intent("android.action.concox.ACTION_CHANGE_SYSTIME");
        Bundle mBundle = new Bundle();
        mBundle.putInt("KEY_YEAR", Integer.parseInt(map.get("KEY_YEAR")));
        mBundle.putInt("KEY_MONTH", Integer.parseInt(map.get("KEY_MONTH")) - 1);
        mBundle.putInt("KEY_DAY", Integer.parseInt(map.get("KEY_DAY")));
        mBundle.putInt("KEY_HOUR", Integer.parseInt(map.get("KEY_HOUR")));
        mBundle.putInt("KEY_MINUTE", Integer.parseInt(map.get("KEY_MINUTE")));
        mIntent.putExtras(mBundle);
        context.sendBroadcast(mIntent);
    }

    public static Map<String, String> getFormatTime3(String millTime) {

        Map<String, String> map = new HashMap<>();
        map.put("KEY_YEAR", getFormatTime(Long.parseLong(millTime), "yyyy"));
        map.put("KEY_MONTH", getFormatTime(Long.parseLong(millTime), "MM"));
        map.put("KEY_DAY", getFormatTime(Long.parseLong(millTime), "dd"));
        map.put("KEY_HOUR", getFormatTime(Long.parseLong(millTime), "HH"));
        map.put("KEY_MINUTE", getFormatTime(Long.parseLong(millTime), "mm"));
        return map;
    }

    public static String getFormatTime_Worksheet(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(str);
            return sDateFormat.format(date);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
